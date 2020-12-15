package com.example.demo.repos;

import com.example.demo.models.Member;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberRepo {
    private Connection connection;

    public MemberRepo() {
        this.connection = DatabaseConnectionManager.getDatabaseConnection();
    }

    public boolean create(Member member){
        try {
            String insertString = "INSERT INTO members (firstName,lastName,isFemale,mail,startDate,birthday,phoneNumber) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertString);
            statement.setString(1,member.getFirstName());
            statement.setString(2,member.getLastName());
            statement.setInt(3,member.getIsFemale());
            statement.setString(4,member.getMail());
            statement.setDate(5,new java.sql.Date(member.getStartDate().getTime()));
            statement.setDate(6,new java.sql.Date(member.getBirthday().getTime()));
            statement.setInt(7, member.getPhoneNumber());
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("error at create() MemberRepo");
            System.out.println(e.getMessage());
        }
        return false;
    }
}
