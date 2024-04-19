package org.zjj.myspring.ioc;

import org.junit.Test;
import org.zjj.myspring.beans.factory.support.ClassPathXmlApplicationContext;

public class InitAndDestroyTest {
    @Test
    public void testInitAndDestroyMethod() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");
        applicationContext.registerShutdownHook();  // or applicationContext.close() manually
    }
}
