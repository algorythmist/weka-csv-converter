package com.tecacet.data.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class UriResourceLoader implements ResourceLoader {

    @Override
    public InputStream loadResource(String path) throws IOException {
        try {
            URI uri = new URI(path);
            return uri.toURL().openStream();
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }
}
