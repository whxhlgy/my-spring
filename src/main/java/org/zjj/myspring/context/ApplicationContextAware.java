package org.zjj.myspring.context;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.Aware;

/**
 * Interface to be implemented by any object that wishes
 * to be notified of the ApplicationContext that it runs in.
 */
public interface ApplicationContextAware extends Aware {

    /**
     * Set the ApplicationContext that this object runs in.
     * @param applicationContext
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
