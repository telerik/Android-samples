package com.telerik.examples.viewmodels;

import java.util.ArrayList;

public class ExampleGroup extends Example {
    private String logo = "drawable/control_logo";
    private String homePageLogo = logo;
    private String drawerIcon = homePageLogo;
    private ArrayList<Example> examples = null;
    private String homePageHeaderResource;

    public ExampleGroup(ExampleGroup parent) {
        super(parent);
    }

    public String getLogoResource() {
        return this.logo;
    }

    public void setLogoResource(String logo) {
        this.logo = logo;
    }

    public String getHomePageHeaderResource() {
        return this.homePageHeaderResource;
    }

    public void setHomePageHeaderResource(String resource) {
        this.homePageHeaderResource = resource;
    }

    public String getHomePageLogoResource() {
        return this.homePageLogo;
    }

    public void setDrawerIcon(String drawerIcon) {
        this.drawerIcon = drawerIcon;
    }

    public String getDrawerIcon() {
        return this.drawerIcon;
    }

    public void setHomePageLogoResource(String homePageLogo) {
        this.homePageLogo = homePageLogo;
    }

    public ArrayList<Example> getExamples() {
        if (this.examples == null) {
            this.examples = new ArrayList<Example>();
        }
        return this.examples;
    }
}
