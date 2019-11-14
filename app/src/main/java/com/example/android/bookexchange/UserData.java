package com.example.android.bookexchange;

public class UserData {
    private String name;
    private String email;
    private String usn;
    private String branch;

    public UserData(String name, String email, String usn, String branch) {
        this.name = name;
        this.email = email;
        this.usn = usn;
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsn() {
        return usn;
    }

    public String getBranch() {
        return branch;
    }
}
