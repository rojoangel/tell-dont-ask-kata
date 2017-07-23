package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.factory.OrderFactory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private Order order;

    @Before
    public void setup() {
        Category food = new Category("food", new BigDecimal("10"));
        Product salad = new Product("salad", new BigDecimal("3.56"), food);
        Product tomato = new Product("tomato", new BigDecimal("4.65"), food);
        order = OrderFactory.created();
        order.add(new OrderItem(salad, 2));
        order.add(new OrderItem(tomato, 3));
    }

    @Test
    public void calculateTotal() throws Exception {
        assertThat(order.calculateTotal(), is(new BigDecimal("23.20")));

    }

    @Test
    public void calculateTax() throws Exception {
        assertThat(order.calculateTax(), is(new BigDecimal("2.13")));
    }

}