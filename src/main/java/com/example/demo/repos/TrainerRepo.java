package com.example.demo.repos;

import com.example.demo.models.Trainer;
import com.example.demo.util.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerRepo {
    private Connection connection;

    public TrainerRepo() {
        this.connection = DatabaseConnectionManager.getDatabaseConnection();
    }

    public boolean updateHours(double hours, int id){
        boolean succes = false;

        String updateString = "UPDATE trainers SET hours=? where id=?";
        Trainer tempTrainer = getTrainerById(id);
        try {
            PreparedStatement statement = connection.prepareStatement(updateString);
            statement.setDouble(1,tempTrainer.getHours() + hours);
            statement.setInt(2,tempTrainer.getId());
            statement.executeUpdate();
            succes = true;
        }
        catch (SQLException e){
            System.out.println("Error at updateTrainerInfo() TrainerRepo");
            System.out.println(e.getMessage());
        }
        return succes;
    }


    public boolean create(Trainer trainer){
        try {
            String insertString = "INSERT INTO trainers (firstName,lastName,hours) VALUES (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(insertString);
            statement.setString(1, trainer.getFirstName());
            statement.setString(2, trainer.getLastName());
            statement.setDouble(3, trainer.getHours());


            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("Error at create() TrainerRepo");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Trainer getTrainerById(int id){
        Trainer trainerToReturn = new Trainer();
        try{
            String selectString = "SELECT * FROM trainers where id=?";
            PreparedStatement statement = connection.prepareStatement(selectString);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                trainerToReturn.setId(resultSet.getInt(1));
                trainerToReturn.setFirstName(resultSet.getString(2));
                trainerToReturn.setLastName(resultSet.getString(3));
                trainerToReturn.setHours(resultSet.getDouble(4));
            }
        }catch (SQLException e){
            System.out.println("Error at getTrainerById() TrainerRepo");
            System.out.println(e.getMessage());
        }
        return trainerToReturn;
    }

    public List<Trainer> getAllTrainers(){
        List<Trainer> allTrainers = new ArrayList<>();
        try{
            String selectString = "SELECT * FROM trainers"; //get all trainers string
            PreparedStatement statement = connection.prepareStatement(selectString);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Trainer tempTrainer = new Trainer();
                tempTrainer.setId(resultSet.getInt(1));
                tempTrainer.setFirstName(resultSet.getString(2));
                tempTrainer.setLastName(resultSet.getString(3));
                tempTrainer.setHours(resultSet.getDouble(4));

                allTrainers.add(tempTrainer);
            }
        }catch (SQLException e){
            System.out.println("Error at getAllTrainers() TrainerRepo");
            System.out.println(e.getMessage());
        }
        return allTrainers;
    }

    public boolean updateTrainerInfo(Trainer trainer){
        boolean succes = false;

        String updateString = "UPDATE trainers SET firstName=?,lastName=?,hours=? where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateString);
            statement.setString(1,trainer.getFirstName());
            statement.setString(2,trainer.getLastName());
            statement.setDouble(3,trainer.getHours());
            statement.setInt(4, trainer.getId());

            statement.executeUpdate();
            succes = true;
        }
        catch (SQLException e){
            System.out.println("Error at updateTrainerInfo() TrainerRepo");
            System.out.println(e.getMessage());
        }
        return succes;
    }

    public boolean deleteTrainer(int id){
        String deleteString = "DELETE FROM trainers WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteString);
            statement.setInt(1,id);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("Error at deleteTrainer() TrainerRepo");
            System.out.println(e.getMessage());
        }
        return false;
    }


}