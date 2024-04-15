package org.zjj.myspring.beans.factory.config;

import org.zjj.myspring.beans.factory.BeanFactory;
import org.zjj.myspring.beans.factory.BeansException;

/**
 * implemented by bean factories that are capable of autowiring, provided that they want to
 * expose this functionality for existing bean instances.
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * apply bean post processors to the given existing bean instance, invoking their
     * postProcessBeforeInitialization methods.
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * apply bean post processors to the given existing bean instance, invoking their
     * postProcessBeforeInitialization methods.
     *
     * @param existingBean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
