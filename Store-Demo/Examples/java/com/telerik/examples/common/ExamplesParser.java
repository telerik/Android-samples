package com.telerik.examples.common;

import com.telerik.examples.viewmodels.ExampleGroup;
import com.telerik.examples.viewmodels.Example;
import com.telerik.examples.viewmodels.GalleryExample;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExamplesParser {

    private static List<Example> cache = null;

    public static List<Example> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {

        // Check if the examples have been parsed before
        if (cache != null)
            return cache;

        List<Example> exampleGroups = null;
        int eventType = parser.getEventType();
        ExampleGroup exampleGroup = null;
        GalleryExample galleryExample = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    exampleGroups = new ArrayList<Example>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (galleryExample == null && name.equals("ExampleGroup")) {
                        exampleGroup = new ExampleGroup(null);
                        parseAttributes(parser, exampleGroup);
                        exampleGroups.add(exampleGroup);
                    } else if (exampleGroup != null && name.equals("Example")) {
                        ExampleGroup parentGroup = galleryExample == null ? exampleGroup : galleryExample;
                        Example example = new Example(parentGroup);
                        parseAttributes(parser, example);
                        parentGroup.getExamples().add(example);
                    } else if (exampleGroup != null && name.equals("GalleryExample")) {
                        galleryExample = new GalleryExample(exampleGroup);
                        parseAttributes(parser, galleryExample);
                        exampleGroup.getExamples().add(galleryExample);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equals("ExampleGroup")) {
                        exampleGroup = null;
                    } else if (name.equals("GalleryExample") && galleryExample != null) {
                        galleryExample = null;
                    }
            }
            eventType = parser.next();
        }
        cache = exampleGroups;
        return exampleGroups;
    }

    private static void parseAttributes(final XmlPullParser parser, final Example example) {
        final int attributesCount = parser.getAttributeCount();

        String attributeName;
        String attributeValue;
        for (int i = 0; i < attributesCount; i++) {
            attributeName = parser.getAttributeName(i);
            attributeValue = parser.getAttributeValue(i);

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
            } else if (attributeName.equals(("HighlightPosition"))) {
                example.setHighlightPosition(Integer.parseInt(attributeValue));
            } else if (attributeName.equals(("Fragment"))) {
                example.setFragmentName(attributeValue);
            } else if (attributeName.equals("Image")) {
                example.setImage(attributeValue);
            } else if (attributeName.equals("Logo")) {
                if (example instanceof ExampleGroup) {
                    ((ExampleGroup) example).setLogoResource(attributeValue);
                }
            } else if (attributeName.equals("HomepageLogo")) {
                if (example instanceof ExampleGroup) {
                    ((ExampleGroup) example).setHomePageLogoResource(attributeValue);
                }
            } else if (attributeName.equals("DrawerIcon")) {
                if (example instanceof ExampleGroup) {
                    ((ExampleGroup) example).setDrawerIcon(attributeValue);
                }
            } else if (attributeName.equals("HomepageHeader")) {
                if (example instanceof ExampleGroup) {
                    ((ExampleGroup) example).setHomePageHeaderResource(attributeValue);
                }
            }
        }
    }
}
