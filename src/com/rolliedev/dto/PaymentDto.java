package com.rolliedev.dto;

import com.rolliedev.entity.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class PaymentDto {

    Long id;
    LocalDateTime date;
    PaymentStatus status;
    String paymentMethod;
    Long orderId;
}
