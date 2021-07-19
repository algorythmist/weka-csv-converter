package com.tecacet.data.weka;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.*;

public class WekaCSVToARFFConverter implements CSVToARFFConverter {

    @Override
    public File convert(InputStream inputStream, String targetPath) throws IOException {
        CSVLoader csvLoader = new CSVLoader();
        csvLoader.setSource(inputStream);
        Instances data = csvLoader.getDataSet();
        File destination = new File(targetPath);
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(destination);
        saver.writeBatch();
        return destination;
    }


    @Override
    public void convert(InputStream csvInputStream, Writer arffOutputWriter) {
        //doesn't seem to be a way to do this with Weka
        throw new UnsupportedOperationException();
    }


}
