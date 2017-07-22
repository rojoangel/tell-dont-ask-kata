package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.useCase.exception.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.useCase.exception.ShippedOrdersCannotBeChangedException;
import it.gabrieletondi.telldontaskkata.useCase.request.OrderApprovalRequest;

public class OrderApprovalUseCase {
    private final OrderRepository orderRepository;

    public OrderApprovalUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(OrderApprovalRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());

        if (order.isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (order.getStatus().equals(OrderStatus.REJECTED)) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        order.setStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
    }
}
