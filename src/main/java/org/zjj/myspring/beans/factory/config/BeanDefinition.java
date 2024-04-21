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
    private static final String SCOPE_PROTOTYPE = "prototype";
    private static final String SCOPE_SINGLETON = "singleton";

    private Class<?> beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;

    public BeanDefinition(Class<?> beanClass) {
        this.beanClass = beanClass;
        propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public void setScope(String scope) {
        this.scope = scope;
        setSingleton(SCOPE_SINGLETON.equals(scope));
        setPrototype(SCOPE_PROTOTYPE.equals(scope));
    }

    public void addPropertyValue(String name, Object value) {
        propertyValues.addPropertyValue(new PropertyValue(name, value));
    }
}
