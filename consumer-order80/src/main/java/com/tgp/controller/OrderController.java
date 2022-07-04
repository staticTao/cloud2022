package com.tgp.controller;

import com.tgp.entity.Payment;
import com.tgp.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    //定义url
    public static final String PAYMENT_URL = "HTTP://PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/payment/create")
    public ResponseEntity<Payment> create(Payment payment){
       return restTemplate.postForEntity(PAYMENT_URL+"/payment/add", payment,Payment.class);
    }
    @GetMapping("/consumer/payment/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable("id") Long id){
//        Payment forObject = restTemplate.getForObject(PAYMENT_URL + "/payment/" + id, Payment.class);
//        System.out.println(forObject);
//        Payment body = restTemplate.getForEntity(PAYMENT_URL + "/payment/" + id, Payment.class).getBody();
//        System.out.println("body "+body);
        return restTemplate.getForEntity(PAYMENT_URL+"/payment/"+id, Payment.class);
    }

    @GetMapping("/consumer/paymentByLb")
    public String getPayment1(){
        //获取某个实例对象 遍历出所有的主机信息
        List<ServiceInstance> instances = discoveryClient.getInstances("PAYMENT-SERVICE");
        ServiceInstance instances1 = loadBalancer.instances(instances);
        URI uri = instances1.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin()
    {
        String result = restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
        return result;
    }
}
