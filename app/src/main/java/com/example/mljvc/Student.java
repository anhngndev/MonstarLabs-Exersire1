package com.example.mljvc;

import android.os.Build;

import java.util.Objects;

public class Student {
    public static int count=0;

    private int id;
    private String name;
    private String born;
    private String phone;
    private String major;
    private String tS;

    private String link;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Student(String name, String born, String phone, String major, String tS) {
        this.id = count;
        this.name = name;
        this.born = born;
        this.phone = phone;
        this.major = major;
        this.tS = tS;

        count++;
        this.link = name +" "+ born + " "+phone +" "+ major + " "+tS;
        this.link = link.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.equals(getName(), student.getName()) &&
                    Objects.equals(getBorn(), student.getBorn()) &&
                    Objects.equals(getPhone(), student.getPhone()) &&
                    Objects.equals(getMajor(), student.getMajor()) &&
                    Objects.equals(gettS(), student.gettS());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(getName(), getBorn(), getPhone(), getMajor(), gettS());
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String gettS() {
        return tS;
    }

    public void settS(String tS) {
        this.tS = tS;
    }
}
