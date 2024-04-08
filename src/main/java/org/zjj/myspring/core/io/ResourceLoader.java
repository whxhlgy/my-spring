package org.zjj.myspring.core.io;

/**
 * Interface meant to be implemented by objects that can load resources.
 *
 * @author zhongjunjie on 2024/4/8
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
