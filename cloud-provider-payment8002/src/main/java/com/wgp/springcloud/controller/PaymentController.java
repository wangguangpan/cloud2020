package com.wgp.springcloud.controller;


import com.wgp.springcloud.entities.CommonResult;
import com.wgp.springcloud.entities.Payment;
import com.wgp.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("create sucess:{}", result);
        if (result > 0) {
            return new CommonResult(200, "insert ok" + serverPort, result);
        } else {
            return new CommonResult(503, "insert failed" + serverPort, null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment paymentById = paymentService.getPaymentById(id);
        log.info("getPaymentById sucess:{}", paymentById);
        if (paymentById != null) {
            return new CommonResult(200, "select ok" + serverPort, paymentById);
        } else {
            return new CommonResult(503, "select failed" + serverPort, null);
        }
    }
    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        services.stream().forEach((a)->{
            log.info("elemt-service"+a);
        });
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        instances.stream().forEach((a)->{
            log.info("getServiceId{}, getHost{}, getPort{}", a.getServiceId(), a.getHost(), a.getPort());
        });
        return this.discoveryClient;
    }
}
