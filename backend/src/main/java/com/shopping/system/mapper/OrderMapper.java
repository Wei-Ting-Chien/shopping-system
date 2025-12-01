package com.shopping.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.shopping.system.model.Order;
import com.shopping.system.model.OrderItem;

@Mapper
public interface OrderMapper {

    // 新增 Order
    void insertOrder(Order order);

    // 傳入 List，跑 circle 去合併 SQL 後批次 insert 多筆資訊
    void insertOrderItems(@Param("items") List<OrderItem> items);

    // 根據 ID 查詢訂單 + 訂單明細
    Order selectById(Integer id);

    // 查詢所有訂單 (不包含明細)
    List<Order> selectAll();
}