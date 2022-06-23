package ru.job4j.pool;

public class User {

    private String userName;
    private String eMail;

    public User(String userName, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
    }

    public String getUserName() {
        return userName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

}
