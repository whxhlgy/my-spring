package org.zjj.myspring.core.io;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Represents a resource (typically a file) located on the classpath.
 * This class is often used to load static resource (like xml file, config file, etc.)
 * from the classpath.
 *
 * @author zhongjunjie on 2024/4/8
 */
@AllArgsConstructor
public class ClassPathResource implements Resource {

    // the path of the resource
    private String path;

    @Override
    public InputStream getInputStream() throws IOException {
        // get resource located in the classpath (if you don't get classLoader, it will get from this package instead)
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(path);
        if (input == null) {
            throw new IOException(path + " cannot be opened");
        }
        return input;
    }
}
