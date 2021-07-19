package com.tecacet.data.io;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceLoader {

    InputStream loadResource(String path) throws IOException;

    static ResourceLoader getDefault() {
        return new CombinedResourceLoader();
    }
}
