package org.zjj.myspring.beans.factory;

import java.io.IOException;
import java.util.Properties;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.PropertyValue;
import org.zjj.myspring.beans.PropertyValues;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.config.BeanFactoryPostProcessor;
import org.zjj.myspring.core.io.DefaultResourceLoader;
import org.zjj.myspring.core.io.Resource;

import lombok.Setter;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    private static final String PLACEHOLDER_SUFFIX = "}";

    private static final String PLACEHOLDER_PREFIX = "${";

    @Setter
    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties properties = loadProperties();

        processProperties(beanFactory, properties);
    }

    /**
     * Load properties from properties file.
     * @return
     */
    private Properties loadProperties() {
        Resource resource = new DefaultResourceLoader().getResource(location);
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
        return properties;
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            resolvePropertyValues(beanDefinition, properties);
        }
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue property : propertyValues.getPropertyValues()) {
            Object v = property.getValue();
            if (v instanceof String) {
                // only support single placeholder
                String strVal = (String) v;
                StringBuffer stringBuffer = new StringBuffer(strVal);
                int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
                int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String propKey = strVal.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
                    String propVal = properties.getProperty(propKey);
                    stringBuffer.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
                    PropertyValue newProperty = new PropertyValue(property.getName(), stringBuffer.toString());
                    propertyValues.addPropertyValue(newProperty);
                }
            }
        }
    }

}
