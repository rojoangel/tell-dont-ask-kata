package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.factory.OrderFactory;
import it.gabrieletondi.telldontaskkata.exception.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.exception.ShippedOrdersCannotBeChangedException;
import it.gabrieletondi.telldontaskkata.useCase.request.OrderApprovalRequest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OrderApprovalUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private final OrderApprovalUseCase useCase = new OrderApprovalUseCase(orderRepository);

    @Test
    public void approvedExistingOrder() throws Exception {
        Order initialOrder = OrderFactory.created();
        orderRepository.addOrder(initialOrder);

        OrderApprovalRequest request = new OrderApprovalRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(OrderFactory.approved()));
    }

    @Test(expected = RejectedOrderCannotBeApprovedException.class)
    public void cannotApproveRejectedOrder() throws Exception {
        Order initialOrder = OrderFactory.rejected();
        orderRepository.addOrder(initialOrder);

        OrderApprovalRequest request = new OrderApprovalRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }

    @Test(expected = ShippedOrdersCannotBeChangedException.class)
    public void shippedOrdersCannotBeApproved() throws Exception {
        Order initialOrder = OrderFactory.shipped();
        orderRepository.addOrder(initialOrder);

        OrderApprovalRequest request = new OrderApprovalRequest(1);

        useCase.run(request);

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }
}
