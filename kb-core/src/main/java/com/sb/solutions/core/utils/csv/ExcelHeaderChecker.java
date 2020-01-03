package com.sb.solutions.core.utils.csv;

import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.sb.solutions.core.exception.ServiceValidationException;

public class ExcelHeaderChecker {
    public void checkNepseHeader(HSSFRow row) {
        row.forEach(c -> {
            if (!(Arrays.asList("companyname", "value", "companycode", "sharetype")
                .contains(c.getStringCellValue().replaceAll("\\s","").toLowerCase()))) {
                throw new ServiceValidationException(c.getStringCellValue() + " " + "is not valid Header");
            }
        });
    }

}
