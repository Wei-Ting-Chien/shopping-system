package com.shopping.system.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Integer id;
    private String customerName;
    private String customerPhone;
    private String customerLocation; // 地址
    private Integer totalAmount;     // 總金額 (後端計算後填入)
    private String status;           // 訂單狀態 (PENDING, PAID...)
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    
    // 關聯屬性：這不是資料庫欄位，但為了方便 Java 操作，我們放一個 List 存明細
    private List<OrderItem> orderItems;

    // Getter and Setter
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
    // To string
	@Override
	public String toString() {
		return "Order [id=" + id + ", customerName=" + customerName + ", customerPhone=" + customerPhone
				+ ", customerLocation=" + customerLocation + ", totalAmount=" + totalAmount + ", status=" + status
				+ ", createAt=" + createAt + ", updateAt=" + updateAt + ", orderItems=" + orderItems + "]";
	} 
    
}