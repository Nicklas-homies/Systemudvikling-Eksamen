package com.example.demo.repos;

import com.example.demo.models.Member;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

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
    
        public boolean quitNotDelete(int id, Date stopDate){
        //bruges når medlemmer stopper i klubben, men vi gemmer deres data, til kontigent, tilskud er søgt og lign.
        String deleteString = "UPDATE members SET isDeleted=1,stopDate=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteString);
            statement.setDate(1,new java.sql.Date(stopDate.getTime()));
            statement.setInt(2,id);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean deletePermanently(int id){
        //sletter permanent når deres data ikke længerer er vigtigt i forhold til kontigent osv.
        String deleteString = "DELETE FROM members WHERE id=? AND isDeleted = 1";
        int succes = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(deleteString);
            statement.setInt(1,id);
            succes = statement.executeUpdate();  //burde returnerer 1 hvis der blev slettet
            //det er ikke muligt at slette en, som stadig er aktiv, sikre at man ikke ved en fejl sletter
            //medlemmer der stadig er aktive!
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        return succes == 1;
    }
    
}
