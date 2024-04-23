package org.zjj.myspring.context.annotation;

import java.lang.reflect.Field;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.PropertyValues;
import org.zjj.myspring.beans.factory.BeanFactory;
import org.zjj.myspring.beans.factory.BeanFactoryAware;
import org.zjj.myspring.beans.factory.ConfigurableListableBeanFactory;
import org.zjj.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;

import cn.hutool.core.bean.BeanUtil;

/**
 * Annotation that can process @Autowired and @Value annotations.
 *
 * author: ZhongJunJie
 */
public class AutowiredAnnotationBeanPostProcessor implements
    InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pValues, Object bean, String beanName) throws BeansException {
        // process @Value
        Class<? extends Object> clz = bean.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                String value = valueAnnotation.value();
                // resolve placeholder
                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // process @Autowired
        for (Field field : fields) {
            Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
            if (autowiredAnnotation == null) {
                continue;
            }
            // process @Qualifier
            Class<?> type = field.getType();
            Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
            Object dependencyBean = null;
            if (qualifierAnnotation != null) {
                String value = qualifierAnnotation.value();
                String dependencyBeanName = value;
                dependencyBean = beanFactory.getBean(dependencyBeanName, type);
            } else {
                dependencyBean = beanFactory.getBean(type);
            }
            BeanUtil.setFieldValue(bean, field.getName(), dependencyBean);
        }
        return pValues;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

}
