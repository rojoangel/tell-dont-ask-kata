package it.gabrieletondi.telldontaskkata.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ProductTest {
    private Product product;

    @Before
    public void setup() {
        Category food = new Category("food", new BigDecimal("10"));
        product = new Product("salad", new BigDecimal("3.56"), food);
    }

    @Test
    public void calculateTax() throws Exception {
        assertThat(product.calculateTax(), is(new BigDecimal("0.36")));
    }

    @Test
    public void calculateTaxedAmount() throws Exception {
        assertThat(product.calculateTaxedAmount(), is(new BigDecimal("3.92")));
    }
}