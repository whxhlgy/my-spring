package org.zjj.myspring.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.zjj.myspring.beans.BeansException;

/**
 * Useful when you need to access files on the local file system or a network file
 * system.
 *
 * @author zhongjunjie on 2024/4/8
 */
public class FileSystemResource implements Resource {

    private String path;

    private File file;

    private Path filePath;

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
        this.filePath = this.file.toPath();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        // the caller need to close the input stream
        try {
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new BeansException("Could not open file [" + this.path + "]", e);
        }
    }
}
