package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private int isFemale;//Burde være boolean, men på den måde SQL virker er det en int i stedet 1 svarer til true, 0 til false
    private String mail;
    private String mail2;
    private int hold;
    private boolean pointStavne;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private int phoneNumber;
    private int phoneNumber2;
    private int phoneNumber3;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date stopDate;
    private int isDeleted;//Burde være boolean, men på den måde SQL virker er det en int i stedet 1 svarer til true, 0 til false


    public Member(int id, String firstName, String lastName, int isFemale, String mail, String mail2, int hold, boolean pointStavne,
                  Date startDate, Date birthday, int phoneNumber, int phoneNumber2, int phoneNumber3) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isFemale = isFemale;
        this.mail = mail;
        this.mail2 = mail2;
        this.hold = hold;
        this.pointStavne = pointStavne;
        this.startDate = startDate;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumber3 = phoneNumber3;
        this.stopDate = stopDate;
        this.isDeleted = isDeleted;
    }

    public Member(String firstName, String lastName, int isFemale, String mail, String mail2, int hold, boolean pointStavne, Date startDate,
                  Date birthday, int phoneNumber, int phoneNumber2, int phoneNumber3) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isFemale = isFemale;
        this.mail = mail;
        this.mail2 = mail2;
        this.hold = hold;
        this.pointStavne = pointStavne;
        this.startDate = startDate;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumber3 = phoneNumber3;
        this.stopDate = stopDate;
        this.isDeleted = isDeleted;
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

    public String getMail2() {
        return mail2;
    }

    public void setMail2(String mail2) {
        this.mail2 = mail2;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public boolean isPointStavne() {
        return pointStavne;
    }

    public void setPointStavne(boolean pointStavne) {
        this.pointStavne = pointStavne;
    }

    public int getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(int phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public int getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setPhoneNumber3(int phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
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
