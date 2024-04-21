package org.zjj.myspring.beans.factory;

/**
 * Marker superinterface indicating that a bean is eligible to be notified
 * by the Spring container of a particular framework object through a callback-style method.
 *
 * call back style method: like setBeanFactory(BeanFactory beanFactory),
 * will be call by Spring Container
 * rather than the bean itself.
 */
public interface Aware {

}

