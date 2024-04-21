package org.zjj.myspring.context.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.BeanFactory;
import org.zjj.myspring.context.ApplicationEvent;
import org.zjj.myspring.context.ApplicationListener;

public class SimpleApplicationEventMulticaster extends AbstractApplicationMulticaster {

    /**
     * create a new SimpleApplicationEventMulticaster for the given BeanFactory
     * @param beanFactory
     */
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> listener : getApplicationListeners()) {
            if (supportsEvent(listener, event)) {
                listener.onApplicationEvent(event);
            }
        }
    }

    /**
     * Does this listener intrest in the given event?
     * @param listener
     * @param event
     * @return
     */
    private boolean supportsEvent(ApplicationListener<ApplicationEvent> listener, ApplicationEvent event) {
        Type type = listener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("class not found: " + className);
        }
        // test if the event is an instance of the class
        return clazz.isAssignableFrom(event.getClass());
    }

}
