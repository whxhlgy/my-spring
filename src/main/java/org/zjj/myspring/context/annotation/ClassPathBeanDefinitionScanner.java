package org.zjj.myspring.context.annotation;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.support.BeanDefinitionRegistry;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

/**
 * Registering corresponding BeanDefinition with a Registry
 */
@AllArgsConstructor
public class ClassPathBeanDefinitionScanner extends ClassPathScaningCandidateComponentProvider {

    private static final String AUTOWIRED_ANNOTATION_BEAN_POST_PROCESSOR_NAME = "autowiredAnnotationBeanPostProcessor";
    private BeanDefinitionRegistry registry;

    public void doScan(String ...basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> bds = findCandidateComponents(basePackage);
            for (BeanDefinition bd : bds) {
                // resolve the scope of the bean
                String beanScope = resolveBeanScope(bd);
                if (StrUtil.isEmpty(beanScope)) {
                    bd.setScope(beanScope);
                }
                // generate the bean name
                String beanName = StrUtil.lowerFirst(bd.getBeanClass().getSimpleName());
                // register the bean definition
                registry.registerBeanDefinition(beanName, bd);
            }
        }

        // register the BeanPostProcessor for processing @Autowired and @Value
        registry.registerBeanDefinition(
                    AUTOWIRED_ANNOTATION_BEAN_POST_PROCESSOR_NAME,
                    new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class
                                      ));
    }

    private String resolveBeanScope(BeanDefinition bd) {
        Class<? extends BeanDefinition> clazz = bd.getClass();
        Scope scope = clazz.getAnnotation(Scope.class);
        if (scope == null) {
            return StrUtil.EMPTY;
        }
        return scope.value();
    }

}
