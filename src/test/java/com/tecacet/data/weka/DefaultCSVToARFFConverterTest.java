package com.tecacet.data.weka;

import org.junit.Test;
import weka.core.Instances;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class DefaultCSVToARFFConverterTest {

    @Test
    public void missingRelation() {

        try {
            CSVToARFFConverter converter = new DefaultCSVToARFFConverter(null);
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("No relation specified.", iae.getMessage());
        }
    }

    @Test
    public void missingAttributes() throws IOException {
        DefaultCSVToARFFConverter converter = new DefaultCSVToARFFConverter("test");
        try {
            converter.convert("ex2data.csv");
            fail();
        } catch (IllegalArgumentException iae) {
            assertEquals("No attributes specified.", iae.getMessage());
        }
    }

    @Test
    public void convertEasy() throws IOException {
        CSVToARFFConverter converter = new WekaCSVToARFFConverter();
        File file = converter.convert("ex2data.csv");

        Instances data = WekaReader.read(file);
        assertEquals(100, data.size());
        assertEquals(3, data.numAttributes());

        file.delete();

    }

    @Test
    public void convert() throws IOException {
        DefaultCSVToARFFConverter converter = new DefaultCSVToARFFConverter("life");
        converter.addStringAttribute("Country");
        converter.addNumericAttribute("Year");
        converter.addNominalAttribute("Status", new String[] { "Developed", "Developing"});
        converter.addNumericAttribute("Life expectancy");
        converter.addNumericAttribute("Adult Mortality");
        converter.addNumericAttribute("infant deaths");
        converter.addNumericAttribute("Alcohol");
        converter.addNumericAttribute("BMI");
        converter.addNumericAttribute("Polio");
        converter.addNumericAttribute("Diphtheria");
        File file = converter.convert("Life Expectancy Data.csv");

        Instances data = WekaReader.read(file);
        assertEquals(2938, data.size());
        assertEquals(10, data.numAttributes());

        file.delete();
    }

}