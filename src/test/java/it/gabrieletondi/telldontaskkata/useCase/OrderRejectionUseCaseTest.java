package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.factory.OrderFactory;
import it.gabrieletondi.telldontaskkata.exception.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.exception.ShippedOrdersCannotBeChangedException;
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
        Order initialOrder = OrderFactory.created();
        orderRepository.addOrder(initialOrder);

        OrderRejectionRequest request = new OrderRejectionRequest(1);

        useCase.run(request);

        final Order savedOrder = orderRepository.getSavedOrder();
        assertThat(savedOrder, is(OrderFactory.rejected()));
    }

    @Test(expected = ApprovedOrderCannotBeRejectedException.class)
    public void cannotRejectApprovedOrder() throws Exception {
        Order initialOrder = OrderFactory.approved();
        orderRepository.addOrder(initialOrder);

        OrderRejectionRequest request = new OrderRejectionRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }

    @Test(expected = ShippedOrdersCannotBeChangedException.class)
    public void shippedOrdersCannotBeRejected() throws Exception {
        Order initialOrder = OrderFactory.shipped();
        orderRepository.addOrder(initialOrder);

        OrderRejectionRequest request = new OrderRejectionRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }
}
