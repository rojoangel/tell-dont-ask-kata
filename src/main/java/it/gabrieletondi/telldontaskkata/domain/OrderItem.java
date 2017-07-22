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
        this.tax = calculateTax();
        this.taxedAmount = calculateTaxedAmount();
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

    public BigDecimal getTax() {
        return tax;
    }

    private BigDecimal calculateTaxedAmount() {
        return calculateUnitaryTaxedAmount().multiply(BigDecimal.valueOf(getQuantity())).setScale(2, HALF_UP);
    }

    private BigDecimal calculateUnitaryTaxedAmount() {
        return product.getPrice().add(calculateUnitaryTax()).setScale(2, HALF_UP);
    }

    private BigDecimal calculateTax() {
        return calculateUnitaryTax().multiply(BigDecimal.valueOf(getQuantity()));
    }

    private BigDecimal calculateUnitaryTax() {
        return getProduct().getPrice().divide(valueOf(100)).multiply(product.getCategory().getTaxPercentage()).setScale(2, HALF_UP);
    }
}
