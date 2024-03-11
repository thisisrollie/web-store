package com.rolliedev.service;

import com.rolliedev.dao.PaymentDao;
import com.rolliedev.dto.PaymentDto;
import com.rolliedev.mapper.CreatePaymentMapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PaymentService {

    private static final PaymentService INSTANCE = new PaymentService();

    private final PaymentDao paymentDao = PaymentDao.getInstance();
    private final CreatePaymentMapper createPaymentMapper = CreatePaymentMapper.getInstance();

    public Long create(PaymentDto paymentDto) {
        var payment = createPaymentMapper.mapFrom(paymentDto);
        paymentDao.save(payment);
        return payment.getId();
    }

    public static PaymentService getInstance() {
        return INSTANCE;
    }
}
