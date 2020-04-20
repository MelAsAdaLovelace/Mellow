package com.mellow.mellow.Helpers;

public class DashboardAssignmentHelper implements Comparable{
    String title, description, due;

    public DashboardAssignmentHelper(String title, String description, String due) {
        this.title = title;
        this.description = description;
        this.due = due;
    }

    public DashboardAssignmentHelper() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    @Override
    public int compareTo(Object o) {
        return this.due.compareTo(((DashboardAssignmentHelper) o).due);
    }
}
