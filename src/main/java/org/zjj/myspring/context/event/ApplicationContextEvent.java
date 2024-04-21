package org.zjj.myspring.context.event;

import org.zjj.myspring.context.ApplicationContext;
import org.zjj.myspring.context.ApplicationEvent;

/**
 * base class for event raised by an ApplicationContext
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
