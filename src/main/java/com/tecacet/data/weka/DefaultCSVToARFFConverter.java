package com.tecacet.data.weka;

import com.tecacet.jflat.CSVReader;
import weka.core.Attribute;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultCSVToARFFConverter implements CSVToARFFConverter {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final Map<String, Attribute> attributes = new LinkedHashMap<>();
    private final ArffLineMerger lineMerger = new ArffLineMerger();

    private final String relation;

    public DefaultCSVToARFFConverter(String relation) {
        if (relation == null) {
            throw new IllegalArgumentException("No relation specified.");
        }
        this.relation = relation;
    }

    public void addNominalAttribute(String name, String[] values) {
        attributes.put(name, new Attribute(name, Arrays.asList(values)));
    }

    public void addNominalAttribute(String name, Class<Enum<?>> enumClass) {
        String[] values = Arrays.stream(enumClass.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        addNominalAttribute(name, values);
    }

    //TODO: support dates

    public void addStringAttribute(String name) {
        attributes.put(name, new Attribute(name, (List<String>) null));
    }

    public void addNumericAttribute(String name) {
        attributes.put(name, new Attribute(name));
    }


    @Override
    public void convert(InputStream csvInputStream, Writer writer) throws IOException {

        if (attributes.isEmpty()) {
            throw new IllegalArgumentException("No attributes specified.");
        }
        writer.append(String.format("@relation %s\n", relation));
        CSVReader<String[]> csvReader = CSVReader.defaultReader();
        final List<String> header = new ArrayList<>();
        csvReader.read(csvInputStream, (record, tokens) -> {
            long row = record.getRowNumber();
            try {
                if (row == 1) {
                    header.addAll(Arrays.stream(tokens).map(String::trim).collect(Collectors.toList()));
                    writeAttributes(writer, header);
                    return;
                }
                String line = lineMerger.merge(header, attributes, tokens);
                writer.append(line + LINE_SEPARATOR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private void writeAttributes(Writer writer, List<String> header) throws IOException {
        for (String name : header) {
            String attribute = getAttribute(name, attributes);
            if (attribute != null) {
                writer.append(attribute + LINE_SEPARATOR);
            }
        }
        writer.append("@data\n");
    }

    private String getAttribute(String name, Map<String, Attribute> attributes) {
        Attribute attribute = attributes.get(name);
        if (attribute != null) {
            return attribute.toString();
        }
        return null;
    }

}
