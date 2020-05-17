package com.wgp.springcloud.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wgp.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfoOk(Integer id){
        return "Thread:" + Thread.currentThread().getName() + "paymentInfoOk,id" + id;
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHander", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfoTimeOut(Integer id){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread:" + Thread.currentThread().getName() + "paymentInfoTimeOut,id" + id;
    }
    public String paymentInfoTimeOutHander(Integer id){
        return "Thread:" + Thread.currentThread().getName() + "paymentInfoTimeOutHander,id" + id;
    }
}
