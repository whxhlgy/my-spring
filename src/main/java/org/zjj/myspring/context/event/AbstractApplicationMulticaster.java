package org.zjj.myspring.context.event;

import java.util.HashSet;
import java.util.Set;

import org.zjj.myspring.beans.BeansException;
import org.zjj.myspring.beans.factory.BeanFactory;
import org.zjj.myspring.beans.factory.BeanFactoryAware;
import org.zjj.myspring.context.ApplicationEvent;
import org.zjj.myspring.context.ApplicationListener;

import lombok.Getter;

public abstract class AbstractApplicationMulticaster
    implements ApplicationEventMulticaster, BeanFactoryAware {

    @Getter
    private final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new HashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;

    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

}
