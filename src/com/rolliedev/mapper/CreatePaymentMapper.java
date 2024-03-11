package com.rolliedev.mapper;

import com.rolliedev.dto.PaymentDto;
import com.rolliedev.entity.Payment;
import com.rolliedev.entity.PaymentMethod;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreatePaymentMapper implements Mapper<PaymentDto, Payment> {

    private static final CreatePaymentMapper INSTANCE = new CreatePaymentMapper();

    @Override
    public Payment mapFrom(PaymentDto object) {
        return Payment.builder()
                .date(object.getDate())
                .status(object.getStatus())
                .paymentMethod(PaymentMethod.valueOf(object.getPaymentMethod()))
                .orderId(object.getOrderId())
                .build();
    }

    public static CreatePaymentMapper getInstance() {
        return INSTANCE;
    }
}
