package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.beans.factory.ConfigurableListableBeanFactory;

/**
 * Abstract refreshable application context.
 * Base class for ApplicationContext implementations which are supposed to support multiple calls
 * to refresh(), creating a new internal bean factory instance every time.
 * Typically (but not necessarily), such a context will be driven by
 * a set of config locations to load bean definitions from.
 *
 * The only method to be implemented by subclasses is loadBeanDefinitions.
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    /**
     * Bean factory for this context.
     */
    private DefaultListableBeanFactory factory;

    /**
     * Load bean definitions into the factory.
     * @param factory
     * @throws BeansException
     */
    public abstract void loadBeanDefinitions(DefaultListableBeanFactory factory) throws BeansException;

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return factory;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    public void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.factory = beanFactory;
    }
}
