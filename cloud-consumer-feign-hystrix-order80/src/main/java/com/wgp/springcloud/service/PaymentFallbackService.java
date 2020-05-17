package com.wgp.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentFeignHystrixService {
    @Override
    public String paymentInfoOk(Integer id) {
        return "PaymentFallbackService-paymentInfoOk";
    }

    @Override
    public String paymentInfoTimeOut(Integer id) {
        return "PaymentFallbackService-paymentInfoTimeOut";
    }
}
