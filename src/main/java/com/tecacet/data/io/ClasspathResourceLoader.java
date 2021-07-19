package com.tecacet.data.io;

import java.io.IOException;
import java.io.InputStream;

public class ClasspathResourceLoader implements ResourceLoader {

    @Override
    public InputStream loadResource(String path) throws IOException {
        InputStream is = ClassLoader.getSystemResourceAsStream(path);
        if (is == null) {
            throw new IOException("Resource does not exist: " + path);
        }
        return is;
    }
}
