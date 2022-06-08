package com.tgp.controller;

import com.tgp.entity.Payment;
import com.tgp.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentFeignController {
    @Resource
    private PaymentFeignService service;

    @GetMapping("consumer/payment/{id}")
    ResponseEntity<Payment> getPayment(@PathVariable("id") Long id){
       return service.queryById(id);
    }

}
