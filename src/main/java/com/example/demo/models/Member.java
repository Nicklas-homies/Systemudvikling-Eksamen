package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private int isFemale;//Burde være boolean, men på den måde SQL virker er det en int i stedet 1 svarer til true, 0 til false
    private String mail;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private int phoneNumber;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date stopDate;
    private int isDeleted;//Burde være boolean, men på den måde SQL virker er det en int i stedet 1 svarer til true, 0 til false

    public Member(int id, String firstName, String lastName, int isFemale, String mail, Date startDate, Date birthday, int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isFemale = isFemale;
        this.mail = mail;
        this.startDate = startDate;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public Member(String firstName, String lastName, int isFemale, String mail, Date startDate, Date birthday, int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isFemale = isFemale;
        this.mail = mail;
        this.startDate = startDate;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public Member() {
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isFemale=" + isFemale +
                ", mail='" + mail + '\'' +
                ", startDate=" + startDate +
                ", birthday=" + birthday +
                ", phoneNumber=" + phoneNumber +
                ", stopDate=" + stopDate +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getIsFemale() {
        return isFemale;
    }

    public void setIsFemale(int isFemale) {
        this.isFemale = isFemale;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
