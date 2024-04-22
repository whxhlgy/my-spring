package org.zjj.myspring.aop;

import org.junit.Test;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;
import org.zjj.myspring.service.WorldService;

public class AutoProxyTest {
    @Test
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }
}
