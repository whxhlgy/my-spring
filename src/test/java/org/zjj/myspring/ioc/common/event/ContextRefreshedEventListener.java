package org.zjj.myspring.ioc.common.event;

import org.zjj.myspring.context.ApplicationListener;
import org.zjj.myspring.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(this.getClass().getName());
    }

}
