package com.tgp.lb.impl;

import com.tgp.lb.LoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component//需要跟主启动类同包，或者在其子孙包下。
public class MyLB implements LoadBalancer {
    private AtomicInteger atomicInteger =new AtomicInteger(0);//保证总能拿到该变量的最新值

    //获取这是第几次访问
    private final int getAndIncrement(){
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current>=2147483647?0:current+1;
        } while(!this.atomicInteger.compareAndSet(current, next));
        System.out.println("*****第几次访问，次数next: "+next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //首先需要获取这是第几次访问
        int i = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(i);
    }
}
