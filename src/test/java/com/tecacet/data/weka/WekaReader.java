package com.tecacet.data.weka;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class WekaReader {

    public static Instances read(String filename) throws IOException {
        Reader reader = new FileReader(filename);
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        return arff.getData();
    }

    public static Instances read(File file) throws IOException {
        Reader reader = new FileReader(file);
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        return arff.getData();
    }
}
