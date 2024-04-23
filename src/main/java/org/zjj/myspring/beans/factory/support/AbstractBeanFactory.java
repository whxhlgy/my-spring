package org.zjj.myspring.beans.factory.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.FactoryBean;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;
import org.zjj.myspring.beans.factory.config.ConfigurableBeanFactory;
import org.zjj.myspring.util.StringValueResolver;

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

    // we need a new cache to store the factoryBean object to avoid name conflict
    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // if the bean is factoryBean, create the bean from the factoryBean
            return getObjectForBeanInstance(sharedInstance, name);
        }
        // if the bean is not in the singleton cache, create a new one
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition);
        return getObjectForBeanInstance(bean, name);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String name) {
        Object object = beanInstance;
        if (beanInstance instanceof FactoryBean) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            try {
                if (factoryBean.isSingleton()) {
                    object = factoryBeanObjectCache.get(name);
                    if (object == null) {
                        object = factoryBean.getObject();
                        factoryBeanObjectCache.put(name, object);
                    }
                } else {
                    object = factoryBean.getObject();
                }
            } catch (Exception e) {
                throw new BeansException("FactoryBean threw exception on object creation", e);
            }
        }
        return object;
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

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);
}
