package org.zjj.myspring.core.io;

import java.io.InputStream;

/**
 * @author zhongjunjie on 2024/4/8
 */
public interface Resource {
    InputStream getInputStream() throws Exception;
}
