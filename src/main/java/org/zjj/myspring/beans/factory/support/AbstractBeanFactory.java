package org.zjj.myspring.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;
import org.zjj.myspring.beans.factory.config.ConfigurableBeanFactory;

import lombok.Getter;

/**
 * An abstract base class implements BeanFactory.
 * It holds a number of bean definition(which inherited from DefaultSingletonRegistry)
 *
 * Subclass need to implements getBean and getBeanDefinition method.
 *
 * @author zhongjunjie on 2024/4/7
 */
public abstract class AbstractBeanFactory extends DefaultSingletonRegistry
    implements ConfigurableBeanFactory {

    @Getter
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) {
            return bean;
        }
        // if the bean is not in the singleton cache, create a new one
        BeanDefinition beanDefinition = getBeanDefinition(name);
        bean = createBean(name, beanDefinition);
        return bean;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor); // remove the old duplicated one
        this.beanPostProcessors.add(beanPostProcessor);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    protected abstract BeanDefinition getBeanDefinition(String beanName);
}
