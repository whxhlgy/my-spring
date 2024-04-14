package org.zjj.myspring.core.io;

/**
 * Strategy interface for loading resources.
 * Strategy type including like classpath, file system, etc.
 *
 * <p>DefaultResourceLoader is a implementation of this interface</p>
 *
 * @author zhongjunjie on 2024/4/8
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
