package org.zjj.myspring.beans.factory;

/**
 * SPI interface to be implemented by most if not all application contexts.
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * Refresh the underlying application context.
     */
    void refresh() throws BeansException;

    /**
     * Release the ApplicationContext and locks, also release the cached singleton beans
     */
    void close();

    /**
     * register a hook for shutdown in JVM
     */
    void registerShutdownHook();
}
