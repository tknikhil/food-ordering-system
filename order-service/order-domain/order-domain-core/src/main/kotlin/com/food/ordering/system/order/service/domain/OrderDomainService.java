package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Resturant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Resturant resturant);

    OrderPaidEvent payOrder(Order order);

    void approvedOrder(Order order);

    OrderCreatedEvent cancelledOrderPayment(Order order, List<String> failureMessage);

    void cancelOrder(Order order,List<String> failureMessage);
}
