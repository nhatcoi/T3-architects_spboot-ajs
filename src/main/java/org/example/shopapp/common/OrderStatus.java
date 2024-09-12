package org.example.shopapp.common;

public enum OrderStatus {
    PENDING("Order is pending."),        // Đơn hàng đang chờ xử lý
    PROCESSING("Order is being processed."), // Đơn hàng đang được xử lý
    SHIPPED("Order has been shipped."),  // Đơn hàng đã được gửi đi
    DELIVERED("Order has been delivered."), // Đơn hàng đã được giao
    CANCELED("Order has been canceled.");  // Đơn hàng đã bị hủy

    private final String text;

    // Constructor để gán giá trị text cho từng trạng thái
    private OrderStatus(String text) {
        this.text = text;
    }

    // Phương thức để lấy mô tả của trạng thái
    public String getText() {
        return text;
    }

    // Phương thức kiểm tra xem đơn hàng đã hoàn thành chưa
    public boolean isCompleted() {
        return this == DELIVERED;
    }

    // Phương thức kiểm tra xem đơn hàng có bị hủy không
    public boolean isCanceled() {
        return this == CANCELED;
    }

    // Phương thức chuyển trạng thái sang trạng thái tiếp theo
    public OrderStatus nextStatus() {
        switch (this) {
            case PENDING:
                return PROCESSING;
            case PROCESSING:
                return SHIPPED;
            case SHIPPED:
                return DELIVERED;
            case DELIVERED:
            case CANCELED:
                return this; // Không có trạng thái tiếp theo cho DELIVERED và CANCELED
            default:
                throw new IllegalStateException("Unknown order status: " + this);
        }
    }
}

