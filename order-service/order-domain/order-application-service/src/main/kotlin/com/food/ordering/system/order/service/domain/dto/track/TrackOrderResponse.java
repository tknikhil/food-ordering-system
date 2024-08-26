package com.food.ordering.system.order.service.domain.dto.track;

import com.food.ordering.system.domain.valueObject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class TrackOrderResponse {
//changes
    private final UUID orderTrackingId;
    private final OrderStatus orderStatus;
    private final List<String> faliureMessage;
}
