package com.sb.solutions.core.constant;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rujan Maharjan on 7/10/2019
 */
public final class CurrentDb {

    private static final String MSSQL = "patch_ms_sql";
    private static final String MYSQL = "patch_mysql";
    private static final String ORACLE = "patch_oracle";



    private CurrentDb() {
    }

    public static String currentConnectedDb(){
        String dbName = "";
        try {
           dbName ="A";
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("dbnsaasdfasdf:::"+dbName);
        return dbName;
    }
}
