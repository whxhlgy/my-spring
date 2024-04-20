package org.zjj.myspring.ioc.bean;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.BeanFactory;
import org.zjj.myspring.beans.factory.BeanFactoryAware;
import org.zjj.myspring.context.ApplicationContext;
import org.zjj.myspring.context.ApplicationContextAware;

import lombok.Data;

/**
 * @author zhongjunjie on 2024/4/7
 */
@Data
public class HelloService implements BeanFactoryAware, ApplicationContextAware {

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

