package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * This factory is responsible for managing bean definitions.
 *
 * @author zhongjunjie on 2024/4/7
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
implements BeanDefinitionRegistry {

    /** Map of bean definition objects, keyed by bean name. */
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }
}
