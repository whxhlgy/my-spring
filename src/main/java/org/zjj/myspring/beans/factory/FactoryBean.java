package org.zjj.myspring.beans.factory;

/**
 * Interface to be implemented by objects used within a BeanFactory
 * which are themselves factories for individual objects.
 * If a bean implements this interface,
 * it is used as a factory for an object to expose,
 * not directly as a bean instance that will be exposed itself
 *
 * By implementing the FactoryBean interface,
 * developers can create complex instances of a bean or
 * perform some additional logical processing before the bean
 * is instantiated.
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    boolean isSingleton();

}
