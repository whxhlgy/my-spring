package org.zjj.myspring.ioc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.zjj.myspring.bean.Car;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;

public class ValueAnnotationTest {
    @Test
    public void testValueAnnotation() throws Exception {
        ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:value-annotation.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
