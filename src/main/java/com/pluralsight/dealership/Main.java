package com.pluralsight.dealership;
import com.pluralsight.dealership.DataManager.VehicleDAO;
import com.pluralsight.dealership.Service.UserInterface;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {

        // Get the username and password
        String username = args[0];
        String password = args[1];

        try (BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl("jdbc:mysql://localhost:3306/dealership");
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            UserInterface ui = new UserInterface(dataSource);
            ui.display();


        } catch (SQLException e) {
            System.out.println("Something went wrong!");;
        }

    }
}