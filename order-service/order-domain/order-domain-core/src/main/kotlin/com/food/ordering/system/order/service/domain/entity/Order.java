package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueObject.*;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueObject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueObject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueObject.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order  extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final ResturantId resturantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItems> items;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> faliureMessages;

    private Order(Builder builder) {
       super.setId(builder.orderId);
        customerId = builder.customerId;
        resturantId = builder.resturantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        faliureMessages = builder.faliureMessages;
    }
    public static Builder builder() {
        return new Builder();
    }


    public CustomerId getCustomerId() { return customerId;}
    public ResturantId getResturantId() {return resturantId;}
    public StreetAddress getDeliveryAddress() {return deliveryAddress;}
    public Money getPrice() {return price;}
    public List<OrderItems> getItems() { return items;}
    public TrackingId getTrackingId() {return trackingId;}
    public OrderStatus getOrderStatus() { return orderStatus;}
    public List<String> getFaliureMessages() {return faliureMessages; }


    private void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        long itemId=1;
        for (OrderItems orderItem:items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
//        validateItemsPrice();
    }

    private void validateInitialOrder() {
        if(orderStatus != null|| getId() !=null){
            throw  new OrderDomainException("Order is not in correct state for initialization!");
        }
    }

    private void validateTotalPrice() {
        if(price ==null || !price.isGreaterThanZero()){
            throw  new OrderDomainException("Total price must be greater than Zero !");
        }
    }
    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private ResturantId resturantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItems> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> faliureMessages;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder resturantId(ResturantId val) {
            resturantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItems> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder faliureMessages(List<String> val) {
            faliureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
