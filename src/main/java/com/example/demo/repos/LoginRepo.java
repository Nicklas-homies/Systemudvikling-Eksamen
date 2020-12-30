package com.example.demo.repos;

import com.example.demo.models.Login;
import com.example.demo.util.DatabaseConnectionManager;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginRepo {
    private Connection connection;

    public LoginRepo(){
        this.connection = DatabaseConnectionManager.getDatabaseConnection();
    }

    public List<Login> readAllLogin(){
        List<Login> listToReturn = new ArrayList();
        try {
            String selectString = "SELECT * FROM login";
            PreparedStatement statement = connection.prepareStatement(selectString);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                listToReturn.add(new Login(resultSet.getInt(1), resultSet.getString(3), resultSet.getString(2), resultSet.getInt(4)));
            }
        }catch (SQLException e){
            System.out.println("Error at readAllLogin() LoginRepo");
            System.out.println(e.getMessage());
        }
        return listToReturn;
    }



    public boolean createLogin(String username, String password, boolean isAdmin){
        try {
            String insertString = "INSERT INTO login (username,password,isAdmin) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertString);
            statement.setString(1, username);
            statement.setString(2, password);
            if (isAdmin) {
                statement.setDouble(3, 1);
            }else{
                statement.setDouble(3, 0);
            }
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("Error at createLogin() LoginRepo");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String isLoggedIn(HttpSession httpSession){
        if (httpSession.getAttribute("loginstatus") != null){
            return "yes";
        }else{
            return  "no";
        }
    }

    public String isAdmin(HttpSession httpSession){
        if (httpSession.getAttribute("adminStatus") != null){
            return "yes";
        }else{
            return  "no";
        }
    }

    public boolean deleteLogin(int id){
        String deleteString = "DELETE FROM login WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteString);
            statement.setInt(1,id);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("Error at deleteLogin() LoginRepo");
            System.out.println(e.getMessage());
        }
        return false;
    }
}
