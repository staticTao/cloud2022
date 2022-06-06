package com.tgp.controller;

import com.tgp.entity.Payment;
import com.tgp.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Payment)表控制层
 *
 * @author makejava
 * @since 2022-06-05 12:02:09
 */
@RestController
@RequestMapping("payment")
public class PaymentController {
    /**
     * 服务对象
     */
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
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

}

