package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.useCase.exception.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.useCase.exception.ShippedOrdersCannotBeChangedException;
import it.gabrieletondi.telldontaskkata.useCase.request.OrderRejectionRequest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OrderRejectionUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private final OrderRejectionUseCase useCase = new OrderRejectionUseCase(orderRepository);

    @Test
    public void rejectedExistingOrder() throws Exception {
        Order initialOrder = new Order();
        initialOrder.setId(1);
        orderRepository.addOrder(initialOrder);

        OrderRejectionRequest request = new OrderRejectionRequest(1);

        useCase.run(request);

        final Order savedOrder = orderRepository.getSavedOrder();
        assertThat(savedOrder.isRejected(), is(true));
    }

    @Test(expected = ApprovedOrderCannotBeRejectedException.class)
    public void cannotRejectApprovedOrder() throws Exception {
        Order initialOrder = new Order();
        initialOrder.approve();
        initialOrder.setId(1);
        orderRepository.addOrder(initialOrder);

        OrderRejectionRequest request = new OrderRejectionRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }

    @Test(expected = ShippedOrdersCannotBeChangedException.class)
    public void shippedOrdersCannotBeRejected() throws Exception {
        Order initialOrder = new Order();
        initialOrder.ship();
        initialOrder.setId(1);
        orderRepository.addOrder(initialOrder);

        OrderRejectionRequest request = new OrderRejectionRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }
}
