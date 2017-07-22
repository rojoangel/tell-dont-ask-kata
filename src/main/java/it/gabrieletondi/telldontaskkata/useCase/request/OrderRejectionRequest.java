package it.gabrieletondi.telldontaskkata.useCase.request;

public class OrderRejectionRequest {
    private int orderId;

    public OrderRejectionRequest(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}
