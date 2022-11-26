package com.prosoft.todaydiary;

public class ListItem {
    private String nameList;
    private String contentsList;

    public String getName() {
        return nameList;
    }

    public void setName(String name) {
        this.nameList = name;
    }

    public String getContentsList() {
        return contentsList;
    }

    public void setContentsList(String contentsList) {
        this.contentsList = contentsList;
    }

    ListItem(String nameList, String contentsList) {
        this.nameList = nameList;
        this.contentsList = contentsList;
    }
}
