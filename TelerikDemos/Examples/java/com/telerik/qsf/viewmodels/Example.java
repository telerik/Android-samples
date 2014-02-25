package com.telerik.qsf.viewmodels;

public class Example {

    private String headerText = "";
    private String descriptionText = "";
    private String exampleInfo = "";
    private String fragmentName = "FallbackExamples";
    private Boolean isNew = false, isHighlighted = false;
    private int image;

    public String getFragmentName() {
        return String.format("com.telerik.qsf.%s", fragmentName);
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

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int imageId) {
        this.image = imageId;
    }
}
