package org.zjj.myspring.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author zhongjunjie on 2024/4/8
 */
public class DefaultResourceLoader implements ResourceLoader {
    /**
     * Obtain Resource by location.
     */
    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // try to treat as URL
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // treat as a file path
                return new FileSystemResource(location);
            }
        }
    }
}
