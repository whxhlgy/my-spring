package org.zjj.myspring.ioc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.zjj.myspring.context.support.ClassPathXmlApplicationContext;
import org.zjj.myspring.bean.Car;
import org.zjj.myspring.bean.Foo;
import org.zjj.myspring.bean.Person;

public class ApplicationContextTest {

    @Test
    public void testApplicationContext() throws Exception {
        ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring.xml");

        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
        //name属性在CustomBeanFactoryPostProcessor中被修改为ivy
        assertThat(person.getName()).isEqualTo("ivy");

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
        //brand属性在CustomerBeanPostProcessor中被修改为lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }

    @Test
    public void testSubclassingGetBean() throws Exception {
        ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:spring.xml");

        Foo person = applicationContext.getBean("person", Foo.class);
        assertThat(person).isNotNull();
    }
}
