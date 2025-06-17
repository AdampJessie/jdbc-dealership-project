package com.pluralsight.dealership.Service;

import com.pluralsight.dealership.Models.*;
import com.pluralsight.dealership.DataManager.*;
import org.apache.commons.dbcp2.BasicDataSource;


import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private VehicleDAO vehicleDAO;
    private ContractDAO contractDAO;

    private Scanner scanner;

    public UserInterface(BasicDataSource dataSource) {

        this.vehicleDAO = new VehicleDAO(dataSource);
        this.contractDAO = new ContractDAO(dataSource);

        scanner = new Scanner(System.in);
    }

    public void display() {
        init();
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

    public void processSellLeaseVehicleRequest() {
        System.out.print("Enter the VIN of the vehicle to sell or lease: ");
        int vin = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle = null;

        for (Vehicle vehicleInDealership : dealership.getAllVehicles()) {
            if (vehicleInDealership.getVin() == vin) {
                vehicle = vehicleInDealership;
            }
        }

        if (vehicle == null) {
            System.out.println("Vehicle not found. Please try again.");
            return;
        }

        System.out.print("Enter the contract date (YYYYMMDD): ");
        String contractDate = scanner.nextLine();

        System.out.print("Enter the customer name: ");
        String customerName = scanner.nextLine();

        System.out.print("Enter the customer email: ");
        String customerEmail = scanner.nextLine();

        System.out.print("Is it a sale or lease? (sale/lease): ");
        String contractType = scanner.nextLine();

        Contract contract;
        if (contractType.equalsIgnoreCase("sale")) {
            System.out.print("Is financing available? (yes/no): ");
            String financeOption = scanner.nextLine();

            double salesTaxAmount = vehicle.getPrice() * 0.05;
            double recordingFee = 100;
            double processingFee = vehicle.getPrice() < 10000 ? 295 : 495;
            boolean finance = financeOption.equalsIgnoreCase("yes");

            contract = new SalesContract(contractDate, customerName, customerEmail, vehicle, salesTaxAmount, recordingFee, processingFee, finance);
        } else if (contractType.equalsIgnoreCase("lease")) {
            double expectedEndingValue = vehicle.getPrice() / 2;
            double leaseFee = vehicle.getPrice() * 0.07;

            contract = new LeaseContract(contractDate, customerName, customerEmail, vehicle, expectedEndingValue, leaseFee);
        } else {
            System.out.println("Invalid contract type. Please try again.");
            return;
        }

        ContractFileManager.saveContract(contract);
        dealership.removeVehicle(vehicle);

        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);

        System.out.println("Contract saved successfully!");
    }

    public void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double min = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double max = scanner.nextDouble();
        List<Vehicle> vehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(vehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByYear(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);
        displayVehicles(vehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int min = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int max = scanner.nextInt();
        List<Vehicle> vehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(vehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();
        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);
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

    private void init() {
        DealershipFileManager manager = new DealershipFileManager();
        dealership = manager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.toString());
        }
    }

}
