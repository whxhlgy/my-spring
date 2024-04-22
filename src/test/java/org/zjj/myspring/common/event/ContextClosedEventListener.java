package org.zjj.myspring.common.event;

import org.zjj.myspring.context.ApplicationListener;
import org.zjj.myspring.context.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(this.getClass().getName());
    }

}
