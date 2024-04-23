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
import org.zjj.myspring.util.StringValueResolver;

import lombok.Setter;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    private static final String PLACEHOLDER_SUFFIX = "}";

    private static final String PLACEHOLDER_PREFIX = "${";

    @Setter
    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties properties = loadProperties();

        // process properties, replace placeholder.
        processProperties(beanFactory, properties);

        // add string value resolver for resolving @Value annotation
        StringValueResolver resolver = new PlaceholderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(resolver);
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
        }

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

    /**
     * check if the property value is a placeholder, if so, resolve it.
     * every beanDefinition will be checked.
     *
     * @param beanDefinition
     * @param properties
     */
    private void resolvePropertyValues(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue property : propertyValues.getPropertyValues()) {
            Object v = property.getValue();
            if (v instanceof String) {
                String strVal = (String) v;
                String resolved = resolvePlaceholder(strVal, properties);
                propertyValues.addPropertyValue(new PropertyValue(property.getName(), resolved));
            }
        }
    }
    private String resolvePlaceholder(String strVal, Properties properties) {
        StringBuffer stringBuffer = new StringBuffer(strVal);
        int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = strVal.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
            String propVal = properties.getProperty(propKey);
            stringBuffer.replace(startIndex, endIndex + PLACEHOLDER_SUFFIX.length(), propVal);
        }
        return stringBuffer.toString();
    }

}
