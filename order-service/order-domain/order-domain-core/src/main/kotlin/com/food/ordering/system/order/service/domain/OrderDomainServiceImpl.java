package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.java.Log;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Log
public class OrderDomainServiceImpl implements  OrderDomainService{

    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setProductOrderInformation(order,restaurant);
        order.validateOrder();
        order.initializeOrder();
        String orderId=order.getId().getValue().toString();
        log.info("Order with id: "+order.getId().getValue()+" is initiated");

        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }


    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id "+order.getId().getValue()+" is paid");
        return new OrderPaidEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approvedOrder(Order order) {
        order.approve();
        log.info("Order with id "+order.getId().getValue()+"is approved");
    }

    @Override
    public OrderCancelledEvent cancelledOrderPayment(Order order, List<String> failureMessage) {
        order.initCancel(failureMessage);
        log.info("Order payment cancel for id "+order.getId().getValue());
        return new OrderCancelledEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {
        order.cancel(failureMessage);
        log.info("Order with id "+order.getId().getValue()+"is cancelled");
    }

    private void validateRestaurant(Restaurant restaurant) {
        if(!restaurant.isActive()){
            throw new OrderDomainException("Restaurant with id  "+restaurant.getId().getValue()+" is  currently not active.");
        }
    }

    private void setProductOrderInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItems -> restaurant.getProducts().forEach(restaurantProduct ->{
            Product currentProduct = orderItems.getProduct();
            if(currentProduct.equals(restaurantProduct)){
                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),restaurantProduct.getPrice());
            }
        }));
    }

}
