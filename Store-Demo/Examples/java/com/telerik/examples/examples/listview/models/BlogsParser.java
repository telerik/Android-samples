package com.telerik.examples.examples.listview.models;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser of blog posts for xml.
 */
public class BlogsParser {

    private static List<BlogPost> cache = null;

    public static List<BlogPost> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {

        // Check if the examples have been parsed before
        if (cache != null) {
            return new ArrayList<BlogPost>(cache);
        }

        List<BlogPost> blogs = null;
        int eventType = parser.getEventType();
        BlogPost blogPost = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    blogs = new ArrayList<BlogPost>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("BlogPost")) {
                        blogPost = new BlogPost();
                        parseAttributes(parser, blogPost);
                        blogs.add(blogPost);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equals("BlogPost")) {
                        blogPost = null;
                    }
            }
            eventType = parser.next();
        }
        cache = new ArrayList<BlogPost>(blogs);
        return blogs;
    }

    private static void parseAttributes(final XmlPullParser parser, final BlogPost blog) {
        final int attributesCount = parser.getAttributeCount();

        String attributeName;
        String attributeValue;
        for (int i = 0; i < attributesCount; i++) {
            attributeName = parser.getAttributeName(i);
            attributeValue = parser.getAttributeValue(i);

            if (attributeName.equals(("Title"))) {
                blog.setTitle(attributeValue);
            } else if (attributeName.equals("Content")) {
                blog.setContent(attributeValue);
            } else if (attributeName.equals("IsFavourite")) {
                blog.setFavourite(Boolean.parseBoolean(attributeValue));
            }
        }
    }
}
