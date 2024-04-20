package org.zjj.myspring.ioc.common;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.PropertyValue;
import org.zjj.myspring.beans.PropertyValues;
import org.zjj.myspring.beans.factory.ConfigurableListableBeanFactory;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.BeanFactoryPostProcessor;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition personBeanDefiniton = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = personBeanDefiniton.getPropertyValues();
        //将person的name属性改为ivy
        propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
    }

}
