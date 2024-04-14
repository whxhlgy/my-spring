package org.zjj.myspring.beans.factory.config;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class BeanDefinition {
    private Class<?> beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }

    public void addPropertyValue(String name, Object value) {
        propertyValues.addPropertyValue(new PropertyValue(name, value));
    }
}
