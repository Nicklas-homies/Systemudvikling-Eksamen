package com.example.demo.repos;

import com.example.demo.util.DatabaseConnectionManager;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginRepo {
    private Connection connection;

    public Map<String, String> readAllLogin(){
        Map<String, String> mapToReturn = new HashMap<>();
        try {
            String selectString = "SELECT * FROM login";
            PreparedStatement statement = connection.prepareStatement(selectString);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                mapToReturn.put(resultSet.getString(2), resultSet.getString(1));
            }
        }catch (SQLException e){
            System.out.println("Error at readAllLogin() LoginRepo");
            System.out.println(e.getMessage());
        }
        return mapToReturn;
    }

    public String isLoggedIn(HttpSession httpSession){
        if (httpSession.getAttribute("loginstatus") != null){
            return "yes";
        }else{
            return  "no";
        }
    }

    public LoginRepo(){
        this.connection = DatabaseConnectionManager.getDatabaseConnection();
    }
}
