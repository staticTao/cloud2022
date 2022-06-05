package com.tgp.controller;

import com.tgp.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {
    //定义url
    public static final String PAYMENT_URL = "http://localhost:8001";
    @Resource
    private RestTemplate restTemplate;
    @GetMapping("/consumer/payment/create")
    public ResponseEntity<Payment> create(Payment payment){
       return restTemplate.postForEntity(PAYMENT_URL+"/payment/add", payment,Payment.class);
    }
    @GetMapping("/consumer/payment/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForEntity(PAYMENT_URL+"/payment/"+id, Payment.class);
    }
}
