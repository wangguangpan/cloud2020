package com.wgp.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wgp.springcloud.service.PaymentFeignHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentInfoTimeOutGlobalFallback")
public class OrderFeignHystrixController {
    @Resource
    private PaymentFeignHystrixService paymentFeignHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    String paymentInfoOk(@PathVariable("id") Integer id){
        String s = paymentFeignHystrixService.paymentInfoOk(id);
        return s;
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    //@HystrixCommand
    String paymentInfoTimeOut(@PathVariable("id") Integer id){
        String s = paymentFeignHystrixService.paymentInfoTimeOut(id);
        return s;
    }

    String paymentInfoTimeOutFallback(@PathVariable("id") Integer id){
        String s = "精确打击消费端降级";
        return s;
    }
    String paymentInfoTimeOutGlobalFallback(){
        String s = "全局消费端降级";
        return s;
    }
}
