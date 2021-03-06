package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Category;
import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.doubles.InMemoryProductCatalog;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.factory.OrderFactory;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import it.gabrieletondi.telldontaskkata.exception.UnknownProductException;
import it.gabrieletondi.telldontaskkata.useCase.request.SellItemRequest;
import it.gabrieletondi.telldontaskkata.useCase.request.SellItemsRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderCreationUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private Category food = new Category("food", new BigDecimal("10"));
    private final ProductCatalog productCatalog = new InMemoryProductCatalog(
            Arrays.<Product>asList(
                    new Product("salad", new BigDecimal("3.56"), food),
                    new Product("tomato", new BigDecimal("4.65"), food)
            )
    );
    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() throws Exception {

        final SellItemsRequest request = new SellItemsRequest();
        request.add(new SellItemRequest("salad", 2));
        request.add(new SellItemRequest("tomato", 3));

        useCase.run(request);

        final Order insertedOrder = orderRepository.getSavedOrder();

        assertThat(insertedOrder, is(buildExpected()));
    }

    private Order buildExpected() {
        Order created = OrderFactory.created();
        created.setId(0);
        created.add(new OrderItem(productCatalog.getByName("salad"), 2));
        created.add(new OrderItem(productCatalog.getByName("tomato"), 3));
        return created;
    }

    @Test(expected = UnknownProductException.class)
    public void unknownProduct() throws Exception {
        SellItemsRequest request = new SellItemsRequest();
        SellItemRequest unknownProductRequest = new SellItemRequest("unknown product", 1);
        request.add(unknownProductRequest);

        useCase.run(request);
    }
}
