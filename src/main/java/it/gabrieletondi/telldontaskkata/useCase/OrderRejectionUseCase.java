package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.useCase.exception.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.useCase.exception.ShippedOrdersCannotBeChangedException;
import it.gabrieletondi.telldontaskkata.useCase.request.OrderRejectionRequest;

public class OrderRejectionUseCase {

    private final OrderRepository orderRepository;

    public OrderRejectionUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(OrderRejectionRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());

        if (order.getStatus().equals(OrderStatus.SHIPPED)) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (order.getStatus().equals(OrderStatus.APPROVED)) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }
}
