package com.pluralsight.dealership.DataManager;

import org.apache.commons.dbcp2.BasicDataSource;

public class ContractDAO {


    private BasicDataSource dataSource;

    public ContractDAO(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

}
