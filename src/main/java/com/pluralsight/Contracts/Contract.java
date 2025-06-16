package com.pluralsight.Contracts;
import com.pluralsight.dealership.Vehicle;

public abstract class Contract {

    private String Date, name, email;
    private Vehicle vehicleSold;
    private double totalPrice, monthlyPayment;

    public Contract(String date, String name, String email, Vehicle vehicleSold) {
        Date = date;
        this.name = name;
        this.email = email;
        this.vehicleSold = vehicleSold;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Vehicle getVehicleSold() {
        return vehicleSold;
    }

    public void setVehicleSold(Vehicle vehicleSold) {
        this.vehicleSold = vehicleSold;
    }

    public abstract double getTotalPrice();

    public abstract double getMonthlyPayment();

    public abstract String toString();
}
