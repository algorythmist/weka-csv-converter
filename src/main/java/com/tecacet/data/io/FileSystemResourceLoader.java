package com.tecacet.data.io;

import java.io.*;

public class FileSystemResourceLoader implements ResourceLoader {

    @Override
    public InputStream loadResource(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        throw new FileNotFoundException("File does not exist: " + file);
    }
}
