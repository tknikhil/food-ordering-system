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
    private List<String> failureMessages;

    private Order(Builder builder) {
       super.setId(builder.orderId);
        customerId = builder.customerId;
        resturantId = builder.resturantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
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
    public List<String> getFaliureMessages() {return failureMessages; }


    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void initializeOrderItems() {
        long itemId=1;
        for (OrderItems orderItem:items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream().map(orderItem ->{
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.Zero,Money::add);

        if(!price.equals(orderItemsTotal)){
            throw new OrderDomainException("Total price :"+price.getAmount()+" is not equal to Order item Total "+orderItemsTotal.getAmount()+" !");
        }
    }

    private void validateItemPrice(OrderItems orderItem) {
        if(!orderItem.isPriceValid()){
            throw new OrderDomainException("Order Item price "+orderItem.getPrice().getAmount()+" is  not valid for Product "+orderItem.getProduct().getId().getValue());
        }
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

    public void pay(){
        if(orderStatus != OrderStatus.PENDING){
            throw new OrderDomainException("Order is not in correct state for pay operation !");
        }
        orderStatus=OrderStatus.PAID;
    }

    public void approve(){
        if(orderStatus !=OrderStatus.PAID){
            throw  new OrderDomainException("Order is not in correct state for approve operation");
        }
        orderStatus=OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages){
        if(orderStatus !=OrderStatus.PAID){
            throw new OrderDomainException("Order is not in correct state for initCancel Operation !");
        }
        orderStatus =OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages){
        if(!(orderStatus==OrderStatus.PENDING||orderStatus==OrderStatus.CANCELLING)){
                throw  new OrderDomainException("Order is not in correct state for cancel operation !");
        }
        orderStatus =OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if(this.failureMessages!=null && failureMessages !=null){
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
        if(this.failureMessages == null){
            this.failureMessages=failureMessages;
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
        private List<String> failureMessages;

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

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
