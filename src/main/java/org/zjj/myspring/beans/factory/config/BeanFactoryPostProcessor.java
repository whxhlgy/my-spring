package org.zjj.myspring.beans.factory.config;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
