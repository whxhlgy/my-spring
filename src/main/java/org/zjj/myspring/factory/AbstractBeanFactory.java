package org.zjj.myspring.factory;

import org.zjj.myspring.factory.config.BeanDefinition;

/**
 * An abstract base class implements BeanFactory.
 *
 * @author zhongjunjie on 2024/4/7
 */
public abstract class AbstractBeanFactory extends DefaultSingletonRegistry implements BeanFactory {
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

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    protected abstract BeanDefinition getBeanDefinition(String beanName);
}
