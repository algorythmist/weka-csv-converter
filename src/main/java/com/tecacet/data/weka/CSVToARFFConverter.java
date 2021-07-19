package com.tecacet.data.weka;

import com.tecacet.data.io.ResourceLoader;

import java.io.*;

public interface CSVToARFFConverter {

    String CSV_EXTENSION = ".csv";
    String WEKA_EXTENSION = ".arrf";

    void convert(InputStream csvInputStream, Writer arffOutputWriter) throws IOException;

    default File convert(InputStream csvInputStream, String targetPath) throws IOException{
        File destination = new File(targetPath);
        try (FileWriter writer = new FileWriter(destination)) {
            convert(csvInputStream, writer);
        }
        return destination;
    }

    default File convert(String csvInputResource, String targetPath) throws IOException {
        InputStream is = ResourceLoader.getDefault().loadResource(csvInputResource);
        if (is == null) {
            throw new IOException("Input resource not found: "+ csvInputResource);
        }
        return convert(is, targetPath);
    }

    default  File convert(String csvInputResource) throws IOException {
        return convert(csvInputResource, getTargetFilename(csvInputResource));
    }

    default String getTargetFilename(String originalFilename) {
        int index = originalFilename.lastIndexOf(CSV_EXTENSION);
        if (index < 0) {
            return originalFilename + WEKA_EXTENSION;
        }
        return originalFilename.substring(0, index) + WEKA_EXTENSION;
    }
}
