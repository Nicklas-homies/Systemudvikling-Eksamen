package com.example.demo.repos;

import com.example.demo.models.Hold;
import com.example.demo.models.Member;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class MemberRepo {
    private Connection connection;

    public MemberRepo() {
        this.connection = DatabaseConnectionManager.getDatabaseConnection();
    }

    public List<Hold> getHold(){
        //kunne flytte denne her metode til et andet repo
        List<Hold> hold = new ArrayList<>();
            try {
                String sqlString = "SELECT * FROM hold";
                PreparedStatement statement = connection.prepareStatement(sqlString);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    Hold holdItem = new Hold();
                    holdItem.setId(resultSet.getInt(1));
                    holdItem.setName(resultSet.getString(2));
                    hold.add(holdItem);
                }
            }
            catch (SQLException e){
                System.out.println("Error at getHold() MemberRepo");
                System.out.println(e.getMessage());
            }
        return hold;
    }

    public boolean create(Member member){
        try {
            String insertString = "INSERT INTO members (firstName,lastName,isFemale,mail,mail2,hold,pointStavne,startDate,birthday,phoneNumber,phoneNumber2,phoneNumber3) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertString);
            statement.setString(1,member.getFirstName());
            statement.setString(2,member.getLastName());
            statement.setInt(3,member.getIsFemale());
            statement.setString(4,member.getMail());
            statement.setString(5,member.getMail2());
            statement.setInt(6,member.getHold());
            if (member.isPointStavne()){
                statement.setBoolean(7,member.isPointStavne());
            }
            else {
                statement.setBoolean(7,false);
            }
            statement.setDate(8,new java.sql.Date(member.getStartDate().getTime()));
            statement.setDate(9,new java.sql.Date(member.getBirthday().getTime()));
            statement.setInt(10, member.getPhoneNumber());
            statement.setInt(11,member.getPhoneNumber2());
            statement.setInt(12,member.getPhoneNumber3());
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("Error at create() MemberRepo");
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean updateMemberInfo(Member member){
        boolean succes = false;

        String updateString = "UPDATE members SET firstName=?,lastName=?,isFemale=?,mail=?,mail2=?,hold=?,pointStavne=?,startDate=?,birthday=?,phoneNumber=?,phoneNumber2=?,phoneNumber3=? where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateString);
            statement.setString(1,member.getFirstName());
            statement.setString(2,member.getLastName());
            statement.setInt(3,member.getIsFemale());
            statement.setString(4,member.getMail());
            statement.setString(5,member.getMail2());
            statement.setInt(6,member.getHold());
            statement.setBoolean(7,member.isPointStavne());
            statement.setDate(8,new java.sql.Date(member.getStartDate().getTime()));
            statement.setDate(9,new java.sql.Date(member.getBirthday().getTime()));
            statement.setInt(10, member.getPhoneNumber());
            statement.setInt(11,member.getPhoneNumber2());
            statement.setInt(12,member.getPhoneNumber3());
            statement.setInt(13,member.getId());
            statement.executeUpdate();
            succes = true;
        }
        catch (SQLException e){
            System.out.println("Error at update() MemberRepo");
            System.out.println(e.getMessage());
        }
        return succes;
    }

    public List<Member> getAllMembers(){
        List<Member> allMembers = new ArrayList<>();
        try{
            String selectString = "SELECT * FROM members"; //get all members string
            PreparedStatement statement = connection.prepareStatement(selectString);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Member tempMember = new Member();
                tempMember.setId(resultSet.getInt(1));
                tempMember.setFirstName(resultSet.getString(2));
                tempMember.setLastName(resultSet.getString(3));
                tempMember.setIsFemale(resultSet.getInt(4));
                tempMember.setMail(resultSet.getString(5));
                tempMember.setMail2(resultSet.getString(6));
                tempMember.setHold(resultSet.getInt(7));
                tempMember.setPointStavne(resultSet.getBoolean(8));
                tempMember.setStartDate(new java.util.Date(resultSet.getDate(9).getTime()));
                tempMember.setBirthday(new java.util.Date(resultSet.getDate(10).getTime()));
                tempMember.setPhoneNumber(resultSet.getInt(11));
                tempMember.setPhoneNumber2(resultSet.getInt(12));
                tempMember.setPhoneNumber3(resultSet.getInt(13));
                if (resultSet.getDate(14) != null){
                    tempMember.setStopDate(new java.util.Date(resultSet.getDate(14).getTime()));
                    tempMember.setIsDeleted(resultSet.getInt(15));
                }

                //could also use constructor to set settings for each user. Don't think it matters.
                allMembers.add(tempMember);
            }
        }catch (SQLException e){
            System.out.println("Error at delMembers() MemberRepo");
            System.out.println(e.getMessage());
        }
        return allMembers;
    }

    public List<Member> getDeleted(){
        ArrayList<Member> delMembers = new ArrayList<>();
        try{
            String selectString = "SELECT * FROM members WHERE isDeleted = ?";
            PreparedStatement statement = connection.prepareStatement(selectString);
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Member tempMember = new Member();
                tempMember.setId(resultSet.getInt(1));
                tempMember.setFirstName(resultSet.getString(2));
                tempMember.setLastName(resultSet.getString(3));
                tempMember.setIsFemale(resultSet.getInt(4));
                tempMember.setMail(resultSet.getString(5));
                tempMember.setMail2(resultSet.getString(6));
                tempMember.setHold(resultSet.getInt(7));
                tempMember.setPointStavne(resultSet.getBoolean(8));
                tempMember.setStartDate(new java.util.Date(resultSet.getDate(9).getTime()));
                tempMember.setBirthday(new java.util.Date(resultSet.getDate(10).getTime()));
                tempMember.setPhoneNumber(resultSet.getInt(11));
                tempMember.setPhoneNumber2(resultSet.getInt(12));
                tempMember.setPhoneNumber3(resultSet.getInt(13));
                tempMember.setStopDate(new java.util.Date(resultSet.getDate(14).getTime()));
                tempMember.setIsDeleted(resultSet.getInt(15));
                delMembers.add(tempMember);
            }
        }catch (SQLException e){
            System.out.println("Error at delMembers() MemberRepo");
            System.out.println(e.getMessage());
        }
        return delMembers;
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
