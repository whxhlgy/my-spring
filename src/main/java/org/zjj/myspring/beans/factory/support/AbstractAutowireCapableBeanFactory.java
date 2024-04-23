package org.zjj.myspring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.lang.reflect.Method;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.PropertyValue;
import org.zjj.myspring.beans.PropertyValues;
import org.zjj.myspring.beans.factory.BeanFactoryAware;
import org.zjj.myspring.beans.factory.BeanReference;
import org.zjj.myspring.beans.factory.DisposableBean;
import org.zjj.myspring.beans.factory.InitializingBean;
import org.zjj.myspring.beans.factory.config.AutowireCapableBeanFactory;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;
import org.zjj.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * This factory is responsible for creating bean instances.
 *
 * @author zhongjunjie on 2024/4/7
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
    implements AutowireCapableBeanFactory {

    @Getter
    private final InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    // create bean from beanDefinition
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        // give BeanPostProcessors a chance to return a proxy instead of the target bean instance
        Object bean = resolveBeforeInstantiation(beanName, beanDefinition);
        if (bean != null) {
            return bean;
        }
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * Apply before-instantiation post-processors, resolving whether there is a
     * before-instantiation shortcut for the specified bean.
     *
     * @param beanName
     * @param beanDefinition
     * @return
     */
    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (bean != null) {
            return applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    private Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) processor)
                                .postProcessBeforeInstantiation(beanClass, beanName);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;

        try {
            bean = createBeanInstance(beanDefinition);
            // Before setting properties, allow post-processors to modify the bean instance.
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            // populate bean with property values
            applyPropertyValues(beanName, bean, beanDefinition);
            // extension point: allow post-process bean before initialization
            initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Initialization of bean failed: " + beanName, e);
        }

        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        if (beanDefinition.isSingleton()) {
            addSingleton(beanName, bean);
        }
        return bean;
    }

    private void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean,
            BeanDefinition beanDefinition) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues pValues = ((InstantiationAwareBeanPostProcessor) processor)
                                         .postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (pValues == null) {
                    continue;
                }
                for (PropertyValue val : pValues.getPropertyValues()) {
                    beanDefinition.getPropertyValues().addPropertyValue(val);
                }
            }
        }
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // only singleton beans need to be registered for destruction
        if (!beanDefinition.isSingleton()) {
            return;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        // automatically inject BeanFactory if the bean implements BeanFactoryAware
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Throwable e) {
            throw new BeansException("Invocation of init method of bean '" + beanName + "' failed", e);
        }

        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return wrappedBean;
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition)
    throws Throwable {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName) &&
                !(bean instanceof InitializingBean && "afterPropertiesSet".equals(initMethodName))) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
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
     * Using Factory method
     */
    private Object createBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
    }
}
