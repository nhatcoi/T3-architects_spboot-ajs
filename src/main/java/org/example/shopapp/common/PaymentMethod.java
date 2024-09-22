package org.example.shopapp.common;

public enum PaymentMethod {
    COD("COD"), // Thanh toán tiền mặt
    BANKING("Banking"); // Thanh toán chuyển khoản

    private final String text;

    // Constructor để gán giá trị text cho từng phương thức thanh toán
    private PaymentMethod(String text) {
        this.text = text;
    }

    // Phương thức để lấy mô tả của phương thức thanh toán
    public String getText() {
        return text;
    }
}
