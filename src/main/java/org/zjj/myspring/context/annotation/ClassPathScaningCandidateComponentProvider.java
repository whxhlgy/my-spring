package org.zjj.myspring.context.annotation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.stereotype.Component;

import cn.hutool.core.util.ClassUtil;

/**
 * A provider for scanning candidate components from a base package.
 */
public class ClassPathScaningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        LinkedHashSet<BeanDefinition> definitions = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            definitions.add(new BeanDefinition(clazz));
        }
        return definitions;
    }
}
