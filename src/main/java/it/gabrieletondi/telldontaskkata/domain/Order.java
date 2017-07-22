package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    private OrderStatus getStatus() {
        return status;
    }

    public boolean isNew() {
        return getStatus().equals(OrderStatus.CREATED);
    }

    public boolean isShipped() {
        return getStatus().equals(OrderStatus.SHIPPED);
    }

    public boolean isApproved() {
        return getStatus().equals(OrderStatus.APPROVED);
    }

    public boolean isRejected() {
        return getStatus().equals(OrderStatus.REJECTED);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void approve() {
        setStatus(OrderStatus.APPROVED);
    }

    public void reject() {
        setStatus(OrderStatus.REJECTED);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
