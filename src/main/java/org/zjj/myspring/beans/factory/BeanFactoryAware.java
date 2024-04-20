package org.zjj.myspring.beans.factory;

import org.zjj.myspring.beans.BeansException;

/**
 * implementa by classes that wish to aware fo their own BeanFactory.
 */
public interface BeanFactoryAware extends Aware {

    /**
     * Callback that supplies the owning factory to a bean instance.
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
