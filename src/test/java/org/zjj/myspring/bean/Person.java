package org.zjj.myspring.bean;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.DisposableBean;
import org.zjj.myspring.beans.factory.InitializingBean;
import org.zjj.myspring.context.annotation.Autowired;
import org.zjj.myspring.stereotype.Component;

import lombok.Data;

/**
 * @author zhongjunjie on 2024/4/8
 */
@Data
@Component
public class Person implements InitializingBean, DisposableBean, Foo {
    private String name;
    private int age;

    @Autowired
    private Car car;
    public void customInitMethod() {
        System.out.println("this is a customInitMethod");
    }

    public void customDestroyMethod() {
        System.out.println("this is a customDestroyMethod");
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy in destroy() method");
    }
    @Override
    public void afterPropertiesSet() throws BeansException {
        System.out.println("doing afterPropertiesSet");
    }
}
