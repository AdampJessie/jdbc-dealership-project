package com.pluralsight.Contracts;

import com.pluralsight.dealership.Vehicle;

public class SalesContract extends Contract {

    private double salesTaxAmount, recordingFee, processingFee;
    private boolean financed;
    private double monthlyPayment;

    public SalesContract(String date, String name, String email, Vehicle vehicleSold, boolean financed) {
        super(date, name, email, vehicleSold);
        this.salesTaxAmount = vehicleSold.getPrice() * 0.05;
        this.recordingFee = 100;
        if (vehicleSold.getPrice() < 10000)
            this.processingFee = 295;
        else this.processingFee = 495;
        this.financed = financed;
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(double salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinanced() {
        return financed;
    }

    public void setFinanced(boolean financed) {
        this.financed = financed;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().getPrice() + salesTaxAmount + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {

        if (!financed)
            return 0;

        double rate;
        int months;
        double vehiclePrice = getVehicleSold().getPrice();

        if (vehiclePrice >= 10000) {
            rate = .0425;
            months = 48;
        } else {
            rate = .0525;
            months = 24;
        }
        double monthlyRate = rate / 12; // Assuming rate is APR

        return Math.round((vehiclePrice * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months)));

    }

    @Override
    public String toString() {
        Vehicle carSold = this.getVehicleSold();
        return String.format("\n%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                "SALE", this.getDate(), this.getName(), this.getEmail(), carSold.getVin(), carSold.getYear(), carSold.getMake(),
                carSold.getModel(), carSold.getVehicleType(), carSold.getColor(), carSold.getOdometer(), carSold.getPrice(), this.getSalesTaxAmount(),
                this.getRecordingFee(), this.getProcessingFee(), this.getTotalPrice(), this.financed, this.getMonthlyPayment());
    }
}
