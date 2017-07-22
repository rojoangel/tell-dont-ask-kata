package it.gabrieletondi.telldontaskkata.useCase.request;

public class OrderApprovalRequest {
    private int orderId;

    public OrderApprovalRequest(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}
