package it.gabrieletondi.telldontaskkata.useCase.request;

public class SellItemRequest {
    private int quantity;
    private String productName;

    public SellItemRequest(String productName, int quantity) {
        this.quantity = quantity;
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}
