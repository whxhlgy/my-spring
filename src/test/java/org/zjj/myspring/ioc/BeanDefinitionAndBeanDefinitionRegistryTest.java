package org.zjj.myspring.ioc;

import org.junit.Test;
import org.zjj.myspring.beans.factory.support.DefaultListableBeanFactory;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.ioc.bean.HelloService;

/**
 * @author zhongjunjie on 2024/4/7
 */
public class BeanDefinitionAndBeanDefinitionRegistryTest {
    @Test
    public void testBeanFactory() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }
}