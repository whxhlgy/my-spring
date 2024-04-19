package org.zjj.myspring.beans.factory;

/**
 * Interface to be implemented by beans that need to react once all their properties
 * have been set by a BeanFactory.
 *
 * for example to perform custom properties checking
 */
public interface InitializingBean {

    /**
     * Invoked by a BeanFactory after it has set all bean properties
     * supplied
     * @throws BeansException
     */
    void afterPropertiesSet() throws BeansException;

}
