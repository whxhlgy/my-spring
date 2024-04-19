package org.zjj.myspring.beans.factory.xml;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.zjj.myspring.beans.PropertyValue;
import org.zjj.myspring.beans.factory.BeanReference;
import org.zjj.myspring.beans.factory.BeansException;
import org.zjj.myspring.beans.factory.config.BeanDefinition;
import org.zjj.myspring.beans.factory.support.AbstractBeanDefinitionReader;
import org.zjj.myspring.beans.factory.support.BeanDefinitionRegistry;
import org.zjj.myspring.core.io.Resource;
import org.zjj.myspring.core.io.ResourceLoader;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;

/**
 * @author zhongjunjie on 2024/4/9
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String DESTORY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";

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
     */
    private void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i) instanceof Element) {
                if (BEAN_ELEMENT.equals(((Element) childNodes.item(i)).getNodeName())) {
                    Element bean = (Element) childNodes.item(i);
                    String id = bean.getAttribute(ID_ATTRIBUTE);
                    String name = bean.getAttribute(NAME_ATTRIBUTE);
                    String className = bean.getAttribute(CLASS_ATTRIBUTE);
                    String initMethodName = bean.getAttribute(INIT_METHOD_ATTRIBUTE);
                    String destoryMethodName = bean.getAttribute(DESTORY_METHOD_ATTRIBUTE);
                    Class<?> clazz = null;

                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new BeansException("Cannot load class " + className, e);
                    }

                    // use id take precedence over name
                    String beanName = StrUtil.isNotEmpty(id) ? id : name;
                    if (StrUtil.isEmpty(beanName)) {
                        // if id and name are both emptye
                        beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                    }
                    BeanDefinition beanDefinition = new BeanDefinition(clazz);
                    beanDefinition.setInitMethodName(initMethodName);
                    beanDefinition.setDestroyMethodName(destoryMethodName);

                    for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                        if (bean.getChildNodes().item(j) instanceof Element)  {
                            Element property = (Element) bean.getChildNodes().item(j);
                            String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                            String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                            String refAttribute = property.getAttribute(REF_ATTRIBUTE);

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
                    }

                    if (this.getRegistry().containsBeanDefinition(beanName)) {
                        // beanName cannot be duplicated
                        throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
                    }

                    getRegistry().registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
    }


    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }
}
