package org.zjj.myspring.context.event;

import org.zjj.myspring.context.ApplicationEvent;
import org.zjj.myspring.context.ApplicationListener;

/**
 * Interface to be implemented by objects that can manage a number of ApplicationListeners,
 * and publish events to them.
 */
public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
