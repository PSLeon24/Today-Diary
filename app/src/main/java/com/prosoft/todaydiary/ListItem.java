package com.prosoft.todaydiary;

public class ListItem {
    private String nameList;
    private String shapeList;
    private String contentsList;

    public String getName() {
        return nameList;
    }

    public void setName(String name) {
        this.nameList = name;
    }

    public String getShapeList() {
        return shapeList;
    }

    public void setShapeList(String shapeList) {
        this.shapeList = shapeList;
    }

    public String getContentsList() {
        return contentsList;
    }

    public void setContentsList(String contentsList) {
        this.contentsList = contentsList;
    }

    ListItem(String nameList, String shapeList, String contentsList) {
        this.nameList = nameList;
        this.shapeList = shapeList;
        this.contentsList = contentsList;
    }
}
