package it.gabrieletondi.telldontaskkata.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderItemTest {

    private OrderItem item;

    @Before
    public void setup() {
        Category food = new Category("food", new BigDecimal("10"));
        Product salad = new Product("salad", new BigDecimal("3.56"), food);
        item = new OrderItem(salad, 2);
    }

    @Test
    public void getTaxedAmount() throws Exception {
        assertThat(item.getTaxedAmount(), is(new BigDecimal("7.84")));
    }

    @Test
    public void getTax() throws Exception {
        assertThat(item.getTax(), is(new BigDecimal("0.72")));
    }
}
