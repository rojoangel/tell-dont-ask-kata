package it.gabrieletondi.telldontaskkata.factory;

import it.gabrieletondi.telldontaskkata.domain.Order;

public class OrderFactory {

    public static Order created() {
        Order order = new Order();
        order.setId(1);
        return order;
    }

    public static Order rejected() {
        Order order = new Order();
        order.setId(1);
        order.reject();
        return order;
    }

    public static Order shipped() {
        Order order = new Order();
        order.setId(1);
        order.approve();
        order.ship();
        return order;
    }

    public static Order approved() {
        Order order = new Order();
        order.setId(1);
        order.approve();
        return order;
    }
}
