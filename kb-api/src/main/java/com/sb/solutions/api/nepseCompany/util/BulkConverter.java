package com.sb.solutions.api.nepseCompany.util;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class BulkConverter {
    public List<NepseCompany> parseNepseCompanyFile(MultipartFile multipartFile) {
        List<NepseCompany> nepseCompanyList = new ArrayList<NepseCompany>();

        @SuppressWarnings("resource")
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(multipartFile.getInputStream());
        } catch (Exception e) {
            return null;
        }
        XSSFSheet workSheet = workbook.getSheetAt(0);
        System.out.println(workSheet.getLastRowNum());
        int i = 0;
        while (i <= workSheet.getLastRowNum()) {
            XSSFRow row = workSheet.getRow(i++);
            XSSFCell cell0 = null;
            XSSFCell cell1 = null;
            if (i == 1) {
                cell0 = row.getCell(0);
                cell1 = row.getCell(1);

                if (!cell0.toString().equalsIgnoreCase("Company Name")
                        || !cell1.toString().equalsIgnoreCase("Value")) {
                    return null;
                }

            } else if (i > 1) {
                NepseCompany nepseCompany = new NepseCompany();
                if (row.getCell(0).toString() != null) {
                    nepseCompany.setCompanyName(row.getCell(0).toString().trim());
                }
                if (row.getCell(1).toString() != null) {
                    nepseCompany.setAmountPerUnit(Double.parseDouble(row.getCell(1).toString()));
                }
                nepseCompanyList.add(nepseCompany);
            }
        }
        return nepseCompanyList;
    }

}

