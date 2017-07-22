package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.exception.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String currency;
    private List<OrderItem> items;
    private OrderStatus status;
    private int id;

    public Order() {
        status = OrderStatus.CREATED;
        items = new ArrayList<>();
        setCurrency("EUR");
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0.00");
        for (OrderItem item : items) {
            total = total.add(item.getTaxedAmount());
        }
        return total;
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

    public void add(OrderItem item) {
        this.items.add(item);
    }

    public BigDecimal getTax() {
        BigDecimal tax = new BigDecimal("0.00");
        for (OrderItem item : items) {
            tax = tax.add(item.getTax());
        }
        return tax;
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

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void ship() {
        if (isNew() || isRejected()) {
            throw new OrderCannotBeShippedException();
        }

        if (isShipped()) {
            throw new OrderCannotBeShippedTwiceException();
        }

        setStatus(OrderStatus.SHIPPED);
    }

    public void approve() {
        if (isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        setStatus(OrderStatus.APPROVED);
    }

    public void reject() {
        if (isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        setStatus(OrderStatus.REJECTED);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (currency != null ? !currency.equals(order.currency) : order.currency != null) return false;
        if (items != null ? !items.equals(order.items) : order.items != null) return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
