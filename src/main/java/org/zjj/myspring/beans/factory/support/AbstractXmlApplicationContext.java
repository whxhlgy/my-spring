package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Load bean definitions from XML resources.
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    public void loadBeanDefinitions(DefaultListableBeanFactory factory) throws BeansException {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            reader.loadBeanDefinitions(configLocations);
        }
    }

    public abstract String[] getConfigLocations();

}
