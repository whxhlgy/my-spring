package org.zjj.myspring.beans.factory.support;

import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.core.io.Resource;
import org.zjj.myspring.core.io.ResourceLoader;

/**
 * Interface for reading bean definition from a resource
 *
 * @author zhongjunjie on 2024/4/10
 */
public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
