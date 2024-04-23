package org.zjj.myspring.aop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;
import org.zjj.myspring.service.WorldService;

public class AutoProxyTest {
    @Test
    public void testPopulateProxyBeanWithPropertyValues() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");

        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
        assertThat(worldService.getName()).isEqualTo("earth");
    }
}
