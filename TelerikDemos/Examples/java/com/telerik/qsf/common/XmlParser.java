package com.telerik.qsf.common;

import com.telerik.qsf.viewmodels.Example;
import com.telerik.qsf.viewmodels.ExampleGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class XmlParser {

    private static ArrayList<ExampleGroup> cache = null;

    public static ArrayList<ExampleGroup> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {

        // Check if the examples have been parsed before
        if(cache != null)
            return cache;

        ArrayList<ExampleGroup> exampleGroups = null;
        int eventType = parser.getEventType();
        ExampleGroup exampleGroup = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    exampleGroups = new ArrayList<ExampleGroup>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("ExampleGroup")) {
                        exampleGroup = new ExampleGroup();
                        parseAttributes(parser, exampleGroup);
                        exampleGroups.add(exampleGroup);
                    } else if (exampleGroup != null && name.equals("Example")) {
                        Example example = new Example();
                        parseAttributes(parser, example);
                        exampleGroup.getExamples().add(example);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("product") && exampleGroup != null) {
                        exampleGroups.add(exampleGroup);
                    }
            }
            eventType = parser.next();
        }
        cache = exampleGroups;
        return exampleGroups;
    }

    private static void parseAttributes(XmlPullParser parser, Example example) {
        int attributesCount = parser.getAttributeCount();
        for (int i = 0; i < attributesCount; i++) {
            String attributeName = parser.getAttributeName(i);
            String attributeValue = parser.getAttributeValue(i);

            if (attributeName.equals(("HeaderText"))) {
                example.setHeaderText(attributeValue);
            } else if (attributeName.equals("ShortDescription")) {
                example.setDescriptionText(attributeValue);
            } else if (attributeName.equals("ExampleInfo")) {
                example.setExampleInfo(attributeValue);
            } else if (attributeName.equals(("IsNew"))) {
                example.setIsNew(Boolean.valueOf(attributeValue));
            } else if (attributeName.equals(("IsHighlighted"))) {
                example.setIsHighlighted(Boolean.valueOf(attributeValue));
            } else if (attributeName.equals(("Fragment"))) {
                example.setFragmentName(attributeValue);
            } else if (attributeName.equals("drawable")) {
                example.setImage(Integer.parseInt(attributeValue.substring(1, attributeValue.length())));
            }
        }
    }
}
