package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.doubles.TestShipmentService;
import it.gabrieletondi.telldontaskkata.factory.OrderFactory;
import it.gabrieletondi.telldontaskkata.exception.OrderCannotBeShippedException;
import it.gabrieletondi.telldontaskkata.exception.OrderCannotBeShippedTwiceException;
import it.gabrieletondi.telldontaskkata.useCase.request.OrderShipmentRequest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OrderShipmentUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private final TestShipmentService shipmentService = new TestShipmentService();
    private final OrderShipmentUseCase useCase = new OrderShipmentUseCase(orderRepository, shipmentService);

    @Test
    public void shipApprovedOrder() throws Exception {
        Order initialOrder = OrderFactory.approved();
        orderRepository.addOrder(initialOrder);

        OrderShipmentRequest request = new OrderShipmentRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(OrderFactory.shipped()));
        assertThat(shipmentService.getShippedOrder(), is(initialOrder));
    }

    @Test(expected = OrderCannotBeShippedException.class)
    public void createdOrdersCannotBeShipped() throws Exception {
        Order initialOrder = OrderFactory.created();
        orderRepository.addOrder(initialOrder);

        OrderShipmentRequest request = new OrderShipmentRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }

    @Test(expected = OrderCannotBeShippedException.class)
    public void rejectedOrdersCannotBeShipped() throws Exception {
        Order initialOrder = OrderFactory.rejected();
        orderRepository.addOrder(initialOrder);

        OrderShipmentRequest request = new OrderShipmentRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }

    @Test(expected = OrderCannotBeShippedTwiceException.class)
    public void shippedOrdersCannotBeShippedAgain() throws Exception {
        Order initialOrder = OrderFactory.shipped();
        orderRepository.addOrder(initialOrder);

        OrderShipmentRequest request = new OrderShipmentRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }
}
