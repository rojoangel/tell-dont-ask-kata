package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class OrderItem {
    private Product product;
    private int quantity;
    private BigDecimal taxedAmount;
    private BigDecimal tax;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        final BigDecimal unitaryTax = getProduct().getPrice().divide(valueOf(100)).multiply(product.getCategory().getTaxPercentage()).setScale(2, HALF_UP);
        final BigDecimal unitaryTaxedAmount = product.getPrice().add(unitaryTax).setScale(2, HALF_UP);
        final BigDecimal taxedAmount = unitaryTaxedAmount.multiply(BigDecimal.valueOf(getQuantity())).setScale(2, HALF_UP);
        final BigDecimal taxAmount = unitaryTax.multiply(BigDecimal.valueOf(getQuantity()));
        setTax(taxAmount);
        setTaxedAmount(taxedAmount);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTaxedAmount() {
        return taxedAmount;
    }

    public void setTaxedAmount(BigDecimal taxedAmount) {
        this.taxedAmount = taxedAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
