package org.zjj.myspring.beans.factory;

import org.zjj.myspring.beans.BeansException;

/**
 * author: ZhongJunJie
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
