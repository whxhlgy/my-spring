package org.zjj.myspring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.zjj.myspring.beans.PropertyValue;
import org.zjj.myspring.beans.factory.BeanReference;
import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.beans.factory.config.AutowireCapableBeanFactory;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;

/**
 * This factory is responsible for creating bean instances.
 *
 * @author zhongjunjie on 2024/4/7
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
    implements AutowireCapableBeanFactory {

    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    // create bean from beanDefinition
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return doCreateBean(beanName, beanDefinition);
    }

    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        try {
            Object bean = createBeanInstance(beanDefinition);
            // populate bean with property values
            applyPropertyValues(beanName, bean, beanDefinition);
            // extension point: allow post-process bean before initialization
            bean = initializeBean(beanName, bean, beanDefinition);
            addSingleton(beanName, bean);
            return bean;
        } catch (Exception e) {
            throw new BeansException("Initialization of bean failed: " + beanName, e);
        }
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // TODO: invoke init method of bean
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {
        // TODO Auto-generated method stub
        System.out.println("invoke init method of bean: " + beanName);
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) {
        Object result = bean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object beanWrapper = processor.postProcessBeforeInitialization(result, beanName);
            // if the processor returns null, indicate that the no subsequent
            // processors should be called
            if (beanWrapper == null) {
                return result;
            }
            result = beanWrapper;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) {
        Object result = bean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object beanWrapper = processor.postProcessAfterInitialization(result, beanName);
            // if the processor returns null, indicate that the no subsequent
            // processors should be called
            if (beanWrapper == null) {
                return result;
            }
            result = beanWrapper;
        }
        return result;
    }

    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue value : beanDefinition.getPropertyValues().getValueList()) {
                String name = value.getName();
                Object v = value.getValue();
                // if the value is a BeanReference, get the bean instance.
                // not support circular reference
                if (v instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) v;
                    v = getBean(beanReference.getBeanName());
                }

                // set value by reflection
                BeanUtil.setFieldValue(bean, name, v);
            }
        } catch (Exception e) {
            throw new BeansException("Error creating bean with name '" + beanName + "'", e);
        }
    }

    /**
     * Create a bean instance, using an appropriate instantiation strategy: 1. Simple instantiation 2.
     * Using Factory method // TODO
     */
    private Object createBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
    }
}
