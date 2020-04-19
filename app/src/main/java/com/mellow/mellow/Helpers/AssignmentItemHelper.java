package com.mellow.mellow.Helpers;

public class AssignmentItemHelper {


        String title, dueDate, descr;

        public AssignmentItemHelper(String descr, String dueDate, String title) {

            this.descr = descr;
            this.dueDate = dueDate;
            this.title = title;
        }

    public AssignmentItemHelper() {
    }

    public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getDescr() {
            return descr;
        }

        public void setDescr(String descr) {
            this.descr = descr;
        }
}
