package it.gabrieletondi.telldontaskkata.factory;

import it.gabrieletondi.telldontaskkata.domain.Order;

public class OrderFactory {
    public static Order shipped() {
        Order order = new Order();
        order.setId(1);
        order.approve();
        order.ship();
        return order;
    }
}
