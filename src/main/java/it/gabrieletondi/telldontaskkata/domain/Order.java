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
        this.currency = "EUR";
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0.00");
        for (OrderItem item : items) {
            total = total.add(item.calculateTaxedAmount());
        }
        return total;
    }

    public String getCurrency() {
        return currency;
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
            tax = tax.add(item.calculateTax());
        }
        return tax;
    }

    public void ship() {
        if (status.equals(OrderStatus.CREATED) || status.equals(OrderStatus.REJECTED)) {
            throw new OrderCannotBeShippedException();
        }

        if (status.equals(OrderStatus.SHIPPED)) {
            throw new OrderCannotBeShippedTwiceException();
        }

        this.status = OrderStatus.SHIPPED;
    }

    public void approve() {
        if (status.equals(OrderStatus.SHIPPED)) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (status.equals(OrderStatus.REJECTED)) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        this.status = OrderStatus.APPROVED;
    }

    public void reject() {
        if (status.equals(OrderStatus.SHIPPED)) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (status.equals(OrderStatus.APPROVED)) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        this.status = OrderStatus.REJECTED;
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

    @Override
    public String toString() {
        return "Order{" +
                "currency='" + currency + '\'' +
                ", items=" + items +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
