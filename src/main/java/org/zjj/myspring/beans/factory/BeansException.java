package org.zjj.myspring.beans.factory;

/**
 * @author zhongjunjie on 2024/4/7
 */
public class BeansException extends RuntimeException {
    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
