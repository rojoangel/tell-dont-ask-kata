package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.exception.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public Order() {
        status = OrderStatus.CREATED;
        setItems(new ArrayList<>());
        setCurrency("EUR");
        setTotal(new BigDecimal("0.00"));
        setTax(new BigDecimal("0.00"));
    }

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
        if (total != null ? !total.equals(order.total) : order.total != null) return false;
        if (currency != null ? !currency.equals(order.currency) : order.currency != null) return false;
        if (items != null ? !items.equals(order.items) : order.items != null) return false;
        if (tax != null ? !tax.equals(order.tax) : order.tax != null) return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = total != null ? total.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (tax != null ? tax.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
