package com.tecacet.data.weka;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.core.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArffLineMerger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String merge(List<String> header, Map<String, Attribute> attributes, String[] tokens) {
        List<String> outTokens = new ArrayList<>(attributes.size());
        for (int i = 0; i < tokens.length; i++) {
            String name = header.get(i).trim();
            Attribute attribute = attributes.get(name);
            if (attribute == null) {
                logger.debug("Attribute {} is not registered", name);
                continue;
            }
            if (StringUtils.isBlank(tokens[i])) {
                outTokens.add("?");
            } else if (attribute.isNumeric()) {
                String number = tokens[i].replaceAll(",", "");
                outTokens.add(number);
            } else {
                outTokens.add("\"" + tokens[i] + "\"");
            }
        }
        return outTokens.stream().collect(Collectors.joining(","));
    }

}
