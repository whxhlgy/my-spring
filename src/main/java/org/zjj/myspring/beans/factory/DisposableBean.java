package org.zjj.myspring.beans.factory;

/**
 * Interface to be implemented by beans that want to release resources on destruction
 */
public interface DisposableBean {

    void destroy() throws Exception;

}
