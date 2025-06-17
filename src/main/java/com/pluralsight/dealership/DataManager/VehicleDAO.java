package com.pluralsight.dealership.DataManager;

import com.pluralsight.dealership.Models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    private BasicDataSource dataSource;

    public VehicleDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }


    public boolean addVehicle(Vehicle vehicle){

        boolean success = false;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     """
                             insert into vehicles 
                             (`VIN`,`make`,`model`,
                              `vehicle_year`,`SOLD`,
                              `color`,`vehicle_type`,
                              `odometer`,`price`) 
                             VALUES
                              (?, ?, ?, ?, 0, ?, ?, ?, ?)
                             """)) {
            preparedStatement.setInt(1, vehicle.getVin());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setString(5, vehicle.getColor());
            preparedStatement.setString(6, vehicle.getVehicleType());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());

            success = (preparedStatement.executeUpdate() != 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public List<Vehicle> getAllVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM vehicles")) {

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    int vin = results.getInt("VIN");
                    int vehicleYear = results.getInt("vehicle_year");
                    String make = results.getString("make");
                    String model = results.getString("model");
                    String vehicleType = results.getString("vehicle_type");
                    String color = results.getString("color");
                    int odometer = results.getInt("odometer");
                    double price = results.getDouble("price");


                    Vehicle vehicle = new Vehicle(vin, vehicleYear, make, model, vehicleType, color, odometer, price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return vehicles;
    }

    public boolean removeVehicle(int vin){

        boolean success = false;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM vehicles WHERE vin = ?")) {
            preparedStatement.setInt(1, vin);

            success = (preparedStatement.executeUpdate() != 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }






}
