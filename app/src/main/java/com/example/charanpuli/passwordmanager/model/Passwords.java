package com.example.charanpuli.passwordmanager.model;

public class Passwords {
    private String image,pname,password;

    public Passwords()
    {

    }

    public Passwords(String image,String pname,String password) {

        this.image = image;
        this.pname=pname;
        this.password=password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
