package com.tecacet.data.weka;

import org.junit.Test;
import weka.classifiers.evaluation.output.prediction.CSV;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class WekaCSVToARFFConverterTest {

    @Test
    public void convert() throws IOException {
        CSVToARFFConverter converter = new WekaCSVToARFFConverter();
        File destination = converter.convert("ex2data.csv");
        System.out.println(destination.getAbsolutePath());
    }

    //Weka can't handle unicode
    @Test(expected = IOException.class)
    public void convertFails() throws IOException {
        CSVToARFFConverter converter = new WekaCSVToARFFConverter();
        converter.convert("Life Expectancy Data.csv");
    }
}