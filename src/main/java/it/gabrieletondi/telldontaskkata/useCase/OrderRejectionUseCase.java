package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.useCase.request.OrderRejectionRequest;

public class OrderRejectionUseCase {

    private final OrderRepository orderRepository;

    public OrderRejectionUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(OrderRejectionRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());
        order.reject();
        orderRepository.save(order);
    }
}
