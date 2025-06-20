package com.pluralsight.dealership.Service;

import com.pluralsight.dealership.Models.*;
import com.pluralsight.dealership.DataManager.*;
import org.apache.commons.dbcp2.BasicDataSource;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private VehicleDAO vehicleDAO;
    private ContractDAO contractDAO;

    private Scanner scanner;

    public UserInterface(BasicDataSource dataSource) {

        this.vehicleDAO = new VehicleDAO(dataSource);
        this.contractDAO = new ContractDAO(dataSource);

        scanner = new Scanner(System.in);
    }

    public void display() {
        boolean quit = false;
        while (!quit) {
            System.out.println("---------- Menu ----------");
            System.out.println("1. Get vehicles by price");
            System.out.println("2. Get vehicles by make and model");
            System.out.println("3. Get vehicles by year");
            System.out.println("4. Get vehicles by color");
            System.out.println("5. Get vehicles by mileage");
            System.out.println("6. Get vehicles by type");
            System.out.println("7. Get all vehicles");
            System.out.println("8. Add vehicle");
            System.out.println("9. Remove vehicle");
            System.out.println("10. Sell/Lease a vehicle");
            System.out.println("99. Quit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processGetByPriceRequest();
                    break;
                case "2":
                    processGetByMakeModelRequest();
                    break;
                case "3":
                    processGetByYearRequest();
                    break;
                case "4":
                    processGetByColorRequest();
                    break;
                case "5":
                    processGetByMileageRequest();
                    break;
                case "6":
                    processGetByVehicleTypeRequest();
                    break;
                case "7":
                    processGetAllVehiclesRequest();
                    break;
                case "8":
                    processAddVehicleRequest();
                    break;
                case "9":
                    processRemoveVehicleRequest();
                    break;
                case "10":
                    processSellLeaseVehicleRequest();
                    break;
                case "99":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter maximum price: ");
        double max = Double.parseDouble(scanner.nextLine());
        List<Vehicle> vehicles = vehicleDAO.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = vehicleDAO.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter maximum year: ");
        int max = Integer.parseInt(scanner.nextLine());
        List<Vehicle> vehicles = vehicleDAO.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        List<Vehicle> vehicles = vehicleDAO.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter maximum mileage: ");
        int max = Integer.parseInt(scanner.nextLine());

        List<Vehicle> vehicles = vehicleDAO.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();

        List<Vehicle> vehicles = vehicleDAO.getVehiclesByType(vehicleType);
        displayVehicles(vehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> vehicles = vehicleDAO.getAllVehicles();

        if (!vehicles.isEmpty())
            displayVehicles(vehicles);
        else System.out.println("No vehicles to display!");
    }

    public void processAddVehicleRequest() {
        int vin = 0;
        boolean enteringVIN = true;
        while (enteringVIN) {
            System.out.print("Enter vehicle vin (unique & 5 digits): ");
            vin = Integer.parseInt(scanner.nextLine());

            int finalVin = vin;
            boolean uniqueVIN = vehicleDAO.getAllVehicles().stream().
                    noneMatch(vehicle -> vehicle.getVin() == finalVin);
            if (uniqueVIN && String.valueOf(vin).length() == 5)
                enteringVIN = false;
            else System.out.println("Error! VIN must be unique and 5 digits!");
        }

        System.out.print("Enter vehicle make: ");
        String make = scanner.nextLine();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();

        System.out.print("Enter vehicle year: ");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter vehicle price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter vehicle color: ");
        String color = scanner.nextLine();

        System.out.print("Enter vehicle mileage: ");
        int mileage = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter vehicle type (Car, Truck, SUV, Motorcycle): ");
        String type = scanner.nextLine();

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, mileage, price);

        if (vehicleDAO.addVehicle(vehicle))
            System.out.println("Vehicle successfully added!");
        else System.out.println("Something went wrong! Vehicle not added.");
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle you wish to remove: ");
        int vin = scanner.nextInt();

       if (vehicleDAO.removeVehicle(vin))
           System.out.println("Vehicle successfully deleted!");
       else System.out.println("Something went wrong! Vehicle not deleted.");
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }

    public void processSellLeaseVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle to sell or lease: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle contractVehicle = null;

        for (Vehicle vehicleInDealership : vehicleDAO.getAllVehicles()) {
            if (vehicleInDealership.getVin() == vin) {
                contractVehicle = vehicleInDealership;
            }
        }

        if (contractVehicle == null) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }

        System.out.print("Is it a sale or lease? (sale/lease): ");
        String contractType = scanner.nextLine();

        System.out.print("Enter the date (YYYY-MM-DD): ");
        String contractDate = scanner.nextLine();

        String leaseEndDate = null;
        if (contractType.equalsIgnoreCase("lease")){
            boolean running = true;
            while (running) {
                System.out.print("Enter the lease end date (YYYY-MM-DD): ");
                leaseEndDate = scanner.nextLine().trim();

                if (leaseEndDate.isEmpty())
                    throw new RuntimeException("Error! Please enter a date!");
                else try {
                    LocalDate.parse(leaseEndDate);
                    running = false;
                } catch (DateTimeException e){
                    System.out.println("Something went wrong!\n" + e);
                }

            }
        }

        System.out.print("Enter the customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter the customer email: ");
        String customerEmail = scanner.nextLine();

        if (contractType.equalsIgnoreCase("sale")) {
            System.out.print("Is financing available? (yes/no): ");
            String financeOption = scanner.nextLine();

            double salesTaxAmount = contractVehicle.getPrice() * 0.05;
            double recordingFee = 100;
            double processingFee = contractVehicle.getPrice() < 10000 ? 295 : 495;
            boolean finance = financeOption.equalsIgnoreCase("yes");

            SalesContract salesContract = new SalesContract(contractDate, customerName, customerEmail, contractVehicle, salesTaxAmount, recordingFee, processingFee, finance);
            if (contractDAO.addSalesContract(salesContract))
                System.out.println("Success! Contract added.");
            else System.out.println("Error! Failed to add contract.");

        } else if (contractType.equalsIgnoreCase("lease")) {
            double expectedEndingValue = contractVehicle.getPrice() / 2;
            double leaseFee = contractVehicle.getPrice() * 0.07;

            LeaseContract leaseContract = new LeaseContract(contractDate, leaseEndDate, customerName, customerEmail, contractVehicle, expectedEndingValue, leaseFee);
            if (contractDAO.addLeaseContract(leaseContract))
                System.out.println("Success! Contract added.");
            else System.out.println("Error! Failed to add contract.");
        } else {
            System.out.println("Invalid contract type. Please try again.");
            return;
        }


        System.out.println("Contract saved successfully!");
    }

}
