package com.mellow.mellow.Helpers;

public class AssignmentItemHelper {


    String title, descr, due, assID;

    public AssignmentItemHelper(String title, String descr, String due, String assID) {
        this.title = title;
        this.descr = descr;
        this.due = due;
        this.assID = assID;
    }

    public String getAssID() {
        return assID;
    }

    public void setAssID(String assID) {
        this.assID = assID;
    }

    public AssignmentItemHelper() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}
