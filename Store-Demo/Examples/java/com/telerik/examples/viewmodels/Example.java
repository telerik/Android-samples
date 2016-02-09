package com.telerik.examples.viewmodels;

public class Example {
    private String headerText = "";
    private String descriptionText = "";
    private String exampleInfo = "";
    private String fragmentName = "FallbackExamples";
    private Boolean isNew = false, isHighlighted = false;
    private String imageResource = "drawable/control_logo";
    private int highlightPosition = 0;
    private ExampleGroup parent;

    public Example(ExampleGroup parent) {
        this.parent = parent;
    }

    public ExampleGroup getParentControl() {
        return this.parent;
    }

    public String getFragmentName() {
        return String.format("com.telerik.examples.%s", fragmentName);
    }

    public String getShortFragmentName() {
        return this.fragmentName;
    }

    public void setExampleInfo(String info) {
        this.exampleInfo = info;
    }

    public String getExampleInfo() {
        return this.exampleInfo;
    }

    public void setFragmentName(String fragment) {
        this.fragmentName = fragment;
    }

    public String getHeaderText() {
        return this.headerText;
    }

    public void setHeaderText(String value) {
        this.headerText = value;
    }

    public Boolean getIsNew() {
        return this.isNew;
    }

    public void setIsNew(Boolean value) {
        this.isNew = value;
    }

    public Boolean getIsHighlighted() {
        return this.isHighlighted;
    }

    public void setIsHighlighted(Boolean value) {
        this.isHighlighted = value;
    }

    public int getHighlightPosition() {
        return highlightPosition;
    }

    public void setHighlightPosition(int highlightPosition) {
        this.highlightPosition = highlightPosition;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getImage() {
        return this.imageResource;
    }

    public void setImage(String imageId) {
        this.imageResource = imageId;
    }
}
