package com.rolliedev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Payment {

    private Long id;
    private LocalDate date;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private Long orderId;
}
