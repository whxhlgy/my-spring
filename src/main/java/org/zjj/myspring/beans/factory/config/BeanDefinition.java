package org.zjj.myspring.beans.factory.config;

import lombok.Data;

import org.zjj.myspring.beans.PropertyValue;
import org.zjj.myspring.beans.PropertyValues;

/**
 * BeanDefinition should contain the information of a bean, such as class, type
 * (singleton, prototype, etc.), and constructor arguments.
 * For simplicity, we only consider the class here.
 *
 * @author zhongjunjie on 2024/4/7
 */
@Data
public class BeanDefinition {
    private Class<?> beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }


    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }


    public void addPropertyValue(String name, Object value) {
        propertyValues.addPropertyValue(new PropertyValue(name, value));
    }
}
