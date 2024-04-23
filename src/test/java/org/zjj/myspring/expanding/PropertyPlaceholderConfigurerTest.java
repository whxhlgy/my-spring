package org.zjj.myspring.expanding;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.zjj.myspring.bean.Car;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;

public class PropertyPlaceholderConfigurerTest {
    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:property-placeholder-configurer.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
