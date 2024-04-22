package org.zjj.myspring.common.event;

import org.zjj.myspring.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {

    public CustomEvent(Object source) {
        super(source);
    }

}
