package org.zjj.myspring.ioc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;
import org.zjj.myspring.ioc.bean.Car;

public class PrototypeBeanTest {
    @Test
    public void testPrototype() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:prototype-bean.xml");

        Car car1 = applicationContext.getBean("car", Car.class);
        Car car2 = applicationContext.getBean("car", Car.class);
        assertThat(car1 != car2).isTrue();
    }
}
