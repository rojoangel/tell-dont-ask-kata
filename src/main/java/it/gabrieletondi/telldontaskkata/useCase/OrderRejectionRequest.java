package it.gabrieletondi.telldontaskkata.useCase;

public class OrderRejectionRequest {
    private int orderId;

    public OrderRejectionRequest(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public boolean isApproved() {
        return false;
    }

}
