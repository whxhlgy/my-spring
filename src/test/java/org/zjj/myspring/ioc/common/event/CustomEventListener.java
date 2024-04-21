package org.zjj.myspring.ioc.common.event;

import org.zjj.myspring.context.ApplicationListener;

public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(this.getClass().getName());
    }

}
