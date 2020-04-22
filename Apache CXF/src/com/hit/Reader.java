package com.hit;

public class Reader{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String name;
    private String password;
    public Reader(String name,String password){
        this.name=name;
        this.password=password;
    }
    public Reader(){}

    @Override
    public String toString() {
        return "Reader{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}