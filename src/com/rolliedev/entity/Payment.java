package com.rolliedev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    private Long id;
    private LocalDateTime date;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private Long orderId;
}
