package org.zjj.myspring.context.support;

import java.util.Collection;
import java.util.Map;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.context.ApplicationEvent;
import org.zjj.myspring.context.ApplicationListener;
import org.zjj.myspring.context.ConfigurableApplicationContext;
import org.zjj.myspring.context.event.ApplicationEventMulticaster;
import org.zjj.myspring.context.event.ContextClosedEvent;
import org.zjj.myspring.context.event.ContextRefreshedEvent;
import org.zjj.myspring.context.event.SimpleApplicationEventMulticaster;
import org.zjj.myspring.beans.factory.ConfigurableListableBeanFactory;
import org.zjj.myspring.beans.factory.config.BeanFactoryPostProcessor;
import org.zjj.myspring.beans.factory.config.BeanPostProcessor;
import org.zjj.myspring.core.io.DefaultResourceLoader;

/**
 * Base class for ApplicationContext implementations.
 * Subclass need to implement the refreshBeanFactory() and getBeanFactory() methods.
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
    implements ConfigurableApplicationContext {

    private static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public void refresh() throws BeansException {
        // Tell the subclass to refresh the internal bean factory.
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // Add a bean post-processor that can inject the ApplicationContext
        // into applicationContextAware beans.
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // Before the bean factory is initialized,
        // let the post-processors apply any bean definitions they need.
        invokeBeanFactoryPostProcessors(beanFactory);

        // Register bean processors that intercept bean creation.
        registerBeanPostProcessors(beanFactory);

        // Initialize the Multicaster for event publishing.
        initApplicationEventMulticaster();

        // Register listeners to receive events.
        registerListeners();

        // pre instantiate singletons
        beanFactory.preInstantiateSingletons();

        // publish the finish refresh event
        finishRefresh();
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        ApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        this.applicationEventMulticaster = applicationEventMulticaster;
        beanFactory.addSingleton(
                       APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
                       applicationEventMulticaster
                   );
    }

    private void registerListeners() {
        Collection<ApplicationListener> listeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener<?> listener : listeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    /**
     * create a bean factory for this context. And load the bean definitions into it.
     */
    public abstract void refreshBeanFactory() throws BeansException;

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public void close() {
        publishEvent(new ContextClosedEvent(this));
        doClose();
    }

    @Override
    public void registerShutdownHook() {
        Thread shutDownHook = new Thread() {
            @Override
            public void run() {
                doDestory();
            }
        };
        Runtime.getRuntime().addShutdownHook(shutDownHook);
    }

    /**
     * Register BeanPostProcessors that intercept bean creation.
     * @param beanFactory
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> processors = beanFactory
            .getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor processor : processors.values()) {
            beanFactory.addBeanPostProcessor(processor);
        }
    }

    /**
     * Invoke the BeanFactoryPostProcessors.
     * @param beanFactory
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> processors = beanFactory
            .getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor processor : processors.values()) {
            processor.postProcessBeanFactory(beanFactory);
        }
    }

    private void doClose() {
        destoryBeans();
    }

    private void destoryBeans() {
        getBeanFactory().destroySingletons();
    }
    private void doDestory() {
        close();
    };
}
