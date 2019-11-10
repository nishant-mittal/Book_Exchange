package com.example.android.bookexchange;

public class Books {

    private String imagePath;
    private String semester;
    private String branch;
    private String bookNames;
    private String systemTime;
    private String dateAndTime;
    private String phoneNumber;
    private String userEmail;

    public Books() {
    }

    public Books(String imagePath, String semester, String branch, String bookNames, String systemTime, String dateAndTime, String phoneNumber, String userEmail) {
        this.imagePath = imagePath;
        this.semester = semester;
        this.branch = branch;
        this.bookNames = bookNames;
        this.systemTime = systemTime;
        this.dateAndTime = dateAndTime;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSemester() {
        return semester;
    }

    public String getBranch() {
        return branch;
    }

    public String getBookNames() {
        return bookNames;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setBookNames(String bookNames) {
        this.bookNames = bookNames;
    }

    public void setSystemTime(String bookNames) {
        this.systemTime = systemTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
