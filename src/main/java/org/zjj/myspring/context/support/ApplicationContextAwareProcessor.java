package org.zjj.myspring.context.support;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;
import org.zjj.myspring.context.ApplicationContext;
import org.zjj.myspring.context.ApplicationContextAware;

import lombok.Data;

@Data
public class ApplicationContextAwareProcessor implements BeanPostProcessor  {

    private ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Inject the ApplicationContext before initialization of the bean.
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(getApplicationContext());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
