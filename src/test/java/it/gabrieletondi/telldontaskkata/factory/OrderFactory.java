package it.gabrieletondi.telldontaskkata.factory;

import it.gabrieletondi.telldontaskkata.domain.Order;

public class OrderFactory {

    public static Order created() {
        Order order = new Order();
        order.setId(1);
        return order;
    }

    public static Order approved() {
        Order order = created();
        order.approve();
        return order;
    }

    public static Order rejected() {
        Order order = created();
        order.reject();
        return order;
    }

    public static Order shipped() {
        Order order = approved();
        order.ship();
        return order;
    }
}
