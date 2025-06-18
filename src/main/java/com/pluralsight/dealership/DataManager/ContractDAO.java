package com.pluralsight.dealership.DataManager;

import com.pluralsight.dealership.Models.Contract;
import com.pluralsight.dealership.Models.LeaseContract;
import com.pluralsight.dealership.Models.SalesContract;
import com.pluralsight.dealership.Models.Vehicle;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ContractDAO {


    private BasicDataSource dataSource;

    public ContractDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addLeaseContract(LeaseContract contract){

        boolean success = false;
        Vehicle contractVehicle = contract.getVehicleSold();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     """
                             INSERT INTO `lease_contracts`
                             (`VIN`,`lease_start`,
                             `lease_end`,`monthly_payment`)
                             VALUES
                             (?, ?, ?, ?);
                             """)) {
            preparedStatement.setInt(1, contractVehicle.getVin());
            preparedStatement.setString(2, contract.getDate());
            preparedStatement.setString(3, contract.getEndDate());
            preparedStatement.setDouble(4, contract.getMonthlyPayment());

            success = (preparedStatement.executeUpdate() != 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public boolean addSalesContract(SalesContract contract){

        boolean success = false;
        Vehicle contractVehicle = contract.getVehicleSold();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     """
                             INSERT INTO `sales_contracts`
                             (`VIN`,`sale_date`,`monthly_payment`)
                             VALUES
                             (?, ?, ?);
                             """)) {
            preparedStatement.setInt(1, contractVehicle.getVin());
            preparedStatement.setString(2, contract.getDate());
            preparedStatement.setDouble(3, contract.getMonthlyPayment());

            success = (preparedStatement.executeUpdate() != 0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }



}
