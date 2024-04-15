package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.factory.BeansException;

import lombok.Getter;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    @Getter
    private String[] configLocations;

    /**
     * Load bean definitions from the specified XML files and refresh the context.
     * @param configLocations
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    /**
     * Load bean definitions from the specified XML files and refresh the context.
     * @param configLocation
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[] {configLocation});
    }

}
