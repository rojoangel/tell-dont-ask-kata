package it.gabrieletondi.telldontaskkata.useCase;

public class OrderRejectionRequest {
    private int orderId;
    private boolean approved;

    public OrderRejectionRequest(int orderId, boolean approved) {
        this.orderId = orderId;
        this.approved = approved;
    }

    public int getOrderId() {
        return orderId;
    }

    public boolean isApproved() {
        return approved;
    }

}
