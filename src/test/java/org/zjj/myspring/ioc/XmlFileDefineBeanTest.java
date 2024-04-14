package org.zjj.myspring.ioc;

import org.junit.Test;
import org.zjj.myspring.beans.factory.support.DefaultListableBeanFactory;
import org.zjj.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.zjj.myspring.ioc.bean.Car;
import org.zjj.myspring.ioc.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhongjunjie on 2024/4/9
 */
public class XmlFileDefineBeanTest {
    @Test
    public void testXmlFile() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getCar().getBrand()).isEqualTo("porsche");

        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
        assertThat(car.getBrand()).isEqualTo("porsche");
    }
}
