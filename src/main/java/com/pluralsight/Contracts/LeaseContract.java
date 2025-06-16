package com.pluralsight.Contracts;

import com.pluralsight.dealership.Vehicle;

public class LeaseContract extends Contract {

    private double expectedEndingValue, monthlyPayment;
    private final double leaseFee;

    public LeaseContract(String date, String name, String email, Vehicle vehicleSold) {
        super(date, name, email, vehicleSold);
        this.expectedEndingValue = vehicleSold.getPrice() * .5;
        this.leaseFee = vehicleSold.getPrice() * .07;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public void setExpectedEndingValue(double expectedEndingValue) {
        this.expectedEndingValue = expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    @Override
    public double getTotalPrice() {
        return (getVehicleSold().getPrice() - this.expectedEndingValue) + this.leaseFee;
    }

    @Override
    public double getMonthlyPayment() {

        double rate = .04;
        int months = 36;
        double vehiclePrice = getVehicleSold().getPrice();

        double monthlyRate = rate / 12; // Assuming rate is APR
        return (vehiclePrice * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    @Override
    public String toString() {
        Vehicle carSold = this.getVehicleSold();
        return String.format("\n%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f",
                "LEASE", this.getDate(), this.getName(), this.getEmail(), carSold.getVin(), carSold.getYear(), carSold.getMake(),
                carSold.getModel(), carSold.getVehicleType(), carSold.getColor(), carSold.getOdometer(), carSold.getPrice(),
                this.getExpectedEndingValue(), this.getLeaseFee(), this.getTotalPrice(), this.getMonthlyPayment());
    }
}
