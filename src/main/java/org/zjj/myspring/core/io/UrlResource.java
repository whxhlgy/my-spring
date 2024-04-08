package org.zjj.myspring.core.io;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Useful when you need to access resources over the network or from remote locations.
 *
 * @author zhongjunjie on 2024/4/8
 */
@RequiredArgsConstructor
public class UrlResource implements Resource {

    private final URL url;

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        } catch (IOException e) {
            throw new IOException("Could not open URL [" + this.url + "]", e);
        }
    }
}
