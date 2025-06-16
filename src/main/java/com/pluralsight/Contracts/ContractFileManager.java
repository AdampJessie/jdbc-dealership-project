package com.pluralsight.Contracts;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractFileManager {

    public void saveContract(Contract contract){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("contracts.csv", true))) {

            bw.write(contract.toString());

            System.out.println("Contract saved successfully!");
        } catch (IOException e) {
            System.out.println("Something went wrong!\n"+e);
        }

    }

}
