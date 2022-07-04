package com.tgp.controller;

import com.tgp.entity.Payment;
import com.tgp.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Payment)表控制层
 *
 * @author makejava
 * @since 2022-06-05 12:02:09
 */
@RestController
@RequestMapping("payment")
@Slf4j
public class PaymentController {
    /**
     * 服务对象
     */
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Payment> queryById(@PathVariable("id") Long id) {
        System.out.println("此次服务端口："+serverPort);
        return ResponseEntity.ok(this.paymentService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param payment 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseEntity<Payment> add(@RequestBody/*添加在这*/ Payment payment) {
        System.out.println("此次服务端口："+serverPort);
        return ResponseEntity.ok(this.paymentService.insert(payment));
    }

    /**
     * 编辑数据
     *
     * @param payment 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Payment> edit(@RequestBody Payment payment) {
        return ResponseEntity.ok(this.paymentService.update(payment));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.paymentService.deleteById(id));
    }

    /**
     @Description: 服务发现
     @Author: TGP
     @Version: v1.0
     @Date: 2022/6/6 16:25
     @Param
     @Return * @return: java.lang.Object
    **/
    @GetMapping(value = "/discovery")
    public Object discovery(){
        //所有微服务
        List<String> services = discoveryClient.getServices();
        services.forEach((e)->{
            System.out.println("element:"+e);
        });
        //获取某个实例对象 遍历出所有的主机信息
        List<ServiceInstance> instances = discoveryClient.getInstances("PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }

        return this.discoveryClient;
    }
    @GetMapping(value = "/lb")
    public String getPaymentLB() {
        return serverPort;//返回服务接口
    }


    @GetMapping("/zipkin")
    public String paymentZipkin() {
        return "hi ,i'am paymentzipkin server fall back，welcome to here, O(∩_∩)O哈哈~";
    }
}

