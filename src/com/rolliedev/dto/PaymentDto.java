package com.rolliedev.dto;

import com.rolliedev.entity.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PaymentDto {

    Long id;
    LocalDate date;
    PaymentStatus status;
    String paymentMethod;
    Long orderId;
}
