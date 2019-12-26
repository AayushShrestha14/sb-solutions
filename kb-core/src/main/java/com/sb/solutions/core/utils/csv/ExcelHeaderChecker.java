package com.sb.solutions.core.utils.csv;

import org.apache.poi.hssf.usermodel.HSSFRow;

public class ExcelHeaderChecker {

    public boolean checkNepseHeader(HSSFRow row) {

        return row.getCell(0).toString().trim().equalsIgnoreCase("Company Name")
            || !row.getCell(1).toString().trim().equalsIgnoreCase("value")
            || !row.getCell(2).toString().trim().equalsIgnoreCase("Company Code")
            || !row.getCell(3).toString().trim().equalsIgnoreCase("ShareType");
    }
}
