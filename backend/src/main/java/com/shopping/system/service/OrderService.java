package com.shopping.system.service;
import com.shopping.system.exception.ShoppingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.system.dto.OrderDto;
import com.shopping.system.mapper.OrderMapper;
import com.shopping.system.mapper.ProductMapper;
import com.shopping.system.model.Order;
import com.shopping.system.model.OrderItem;
import com.shopping.system.model.Product;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 1. 確認商品是否存在 + 是否上架
     * 2. 確認庫存是否足夠，決定扣除或失敗
     * 3. 計算總金額後寫入
     * 4. 建立訂單主檔案，然後建立明細
     */
    @Transactional // All Success or Fail RollBack
    public Integer createOrder(OrderDto.CreateRequest request) {
        
        List<OrderItem> orderItems = new ArrayList<>();
        int totalAmount = 0;

        // Check Item Info
        for (OrderDto.OrderItemRequest itemRequest : request.items()) {
            
            // Get Product info "From DB"
            Product product = productMapper.selectById(itemRequest.productId());
            
            if (product == null) {
                throw new ShoppingException(HttpStatus.NOT_FOUND, "商品不存在: ID " + itemRequest.productId());
            }
            
            if (!product.getStatus()) {
                throw new ShoppingException("商品已下架: " + product.getName());
            }

            // StockCheck
            if (product.getStock() < itemRequest.quantity()) {
            		throw new ShoppingException("庫存不足: " + product.getName() + " 剩餘 " + product.getStock());
            }

            // StockChange
            product.setStock(product.getStock() - itemRequest.quantity());
            productMapper.update(product);

            // Calculate Amount
            int itemTotal = product.getPrice() * itemRequest.quantity();
            totalAmount += itemTotal;

            // SetUp OrderItem Instance
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.quantity());
            
            orderItems.add(orderItem);
        }

        // --- 階段二：建立訂單主檔 ---
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setCustomerPhone(request.customerPhone());
        order.setCustomerLocation(request.customerLocation());
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");

        // Update DB
        orderMapper.insertOrder(order);

        // 把 OrderID 放到 OItem 內
        for (OrderItem item : orderItems) {
            item.setOrderId(order.getId());
        }

        // Write Order Detail
        orderMapper.insertOrderItems(orderItems);

        return order.getId();
    }

    // Get All Order
    public List<Order> getAllOrders() {
        return orderMapper.selectAll();
    }

    // Get Order with Detail ById
    public Order getOrderById(Integer id) {
        return orderMapper.selectById(id);
    }
}