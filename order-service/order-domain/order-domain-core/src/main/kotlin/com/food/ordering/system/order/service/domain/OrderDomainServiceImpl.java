package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Resturant;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements  OrderDomainService{


    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Resturant resturant) {
        validateResturant(resturant);
        setProductOrderInformation(order,resturant);
        order.validateOrder();
        order.initializeOrder();
        String orderId=order.getId().getValue().toString();
//        log.info("Order with id: {} is initiated",orderId);
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }


    @Override
    public OrderPaidEvent payOrder(Order order) {
        return null;
    }

    @Override
    public void approvedOrder(Order order) {

    }

    @Override
    public OrderCreatedEvent cancelledOrderPayment(Order order, List<String> failureMessage) {
        return null;
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {

    }

    private void validateResturant(Resturant resturant) {
    }

    private void setProductOrderInformation(Order order, Resturant resturant) {
    }

}
