package org.zjj.myspring.beans.factory.xml;

import java.io.InputStream;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.PropertyValue;
import org.zjj.myspring.beans.factory.BeanReference;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.support.AbstractBeanDefinitionReader;
import org.zjj.myspring.beans.factory.support.BeanDefinitionRegistry;
import org.zjj.myspring.context.annotation.ClassPathBeanDefinitionScanner;
import org.zjj.myspring.core.io.Resource;
import org.zjj.myspring.core.io.ResourceLoader;

import cn.hutool.core.util.StrUtil;

/**
 * @author zhongjunjie on 2024/4/9
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    private static final String COMPONENT_SCAN_ELEMENT = "component-scan";
    public static final String DESTORY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String SCOPE_ATTRIBUTE = "scope";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }


    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actually load bean definitions from the specified XML file.
     * @throws DocumentException
     */
    private void doLoadBeanDefinitions(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // resolve tag <context:component-scan>
        Element scan = root.element(COMPONENT_SCAN_ELEMENT);
        if (scan != null) {
            String basePath = scan.attributeValue(BASE_PACKAGE_ATTRIBUTE);
            if (basePath == null) {
                throw new BeansException("The base-package attribute is required");
            }
            scanPackage(basePath);
        }

        for (Iterator<Element> it = root.elementIterator(BEAN_ELEMENT); it.hasNext(); ) {
            Element next = it.next();
            String id = next.attributeValue(ID_ATTRIBUTE);
            String name = next.attributeValue(NAME_ATTRIBUTE);
            String className = next.attributeValue(CLASS_ATTRIBUTE);
            String initMethodName = next.attributeValue(INIT_METHOD_ATTRIBUTE);
            String destroyMethodName = next.attributeValue(DESTORY_METHOD_ATTRIBUTE);
            String scope = next.attributeValue(SCOPE_ATTRIBUTE);
            Class<?> clazz = null;

            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeansException("Cannot load class " + className, e);
            }

            // use id take precedence over name
            String beanName = id != null ? id : name;
            if (beanName == null) {
                // if id and name are both emptye
                beanName = clazz.getSimpleName();
            }
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(scope)) {
                beanDefinition.setScope(scope);
            }

            for (Iterator<Element> it2 = next.elementIterator(PROPERTY_ELEMENT); it2.hasNext(); ) {
                Element property = it2.next();
                String nameAttribute = property.attributeValue(NAME_ATTRIBUTE);
                String valueAttribute = property.attributeValue(VALUE_ATTRIBUTE);
                String refAttribute = property.attributeValue(REF_ATTRIBUTE);

                if (StrUtil.isEmpty(nameAttribute)) {
                    throw new BeansException("The name attribute is required");
                }

                Object value = valueAttribute;
                if (StrUtil.isNotEmpty(refAttribute)) {
                    value = new BeanReference(refAttribute);
                }
                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                // beanName cannot be duplicated
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }


    private void scanPackage(String basePath) {
        String[] basePackages = StrUtil.splitToArray(basePath, ",");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }
}
