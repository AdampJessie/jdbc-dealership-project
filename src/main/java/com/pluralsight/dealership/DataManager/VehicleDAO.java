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

    public List<Vehicle> getVehiclesByPrice(double minPrice, double maxPrice){
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM dealership.vehicles " +
                             "WHERE price >= ? AND price <= ?;")) {

            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);

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

    public List<Vehicle> getVehiclesByMakeModel(String searchMake, String searchModel){
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM vehicles " +
                             "WHERE make LIKE ? AND model LIKE ?")) {
            statement.setString(1, searchMake);
            statement.setString(2, searchModel);

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

    public List<Vehicle> getVehiclesByYear(int minYear, int maxYear){
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM vehicles " +
                             "WHERE vehicle_year >= ? AND vehicle_year <= ?")) {
            statement.setInt(1, minYear);
            statement.setInt(2, maxYear);

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

    public List<Vehicle> getVehiclesByColor(String searchColor){
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM vehicles " +
                             "WHERE color LIKE ?")) {
            statement.setString(1, searchColor);

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

    public List<Vehicle> getVehiclesByMileage(int minMile, int maxMile){
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM vehicles " +
                             "WHERE odometer >= ? AND odometer <= ?")) {

            statement.setInt(1, minMile);
            statement.setInt(2, maxMile);


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

    public List<Vehicle> getVehiclesByType(String searchType){
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM vehicles " +
                             "WHERE vehicle_type LIKE ?")) {
            statement.setString(1, searchType);

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
