package com.mellow.mellow.Helpers;

public class DashboardAssignmentHelper {
    int image;
    String title, description;

    public DashboardAssignmentHelper(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


}
