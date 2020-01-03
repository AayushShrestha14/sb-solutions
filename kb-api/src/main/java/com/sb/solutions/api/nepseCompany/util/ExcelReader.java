package com.sb.solutions.api.nepseCompany.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.core.enums.ShareType;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.csv.ExcelHeaderChecker;
import com.sb.solutions.core.utils.file.FileUploadUtils;

@Component
public class ExcelReader {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
    private ExcelHeaderChecker excelHeaderChecker = new ExcelHeaderChecker();

    public List<NepseCompany> parseNepseCompanyFile(MultipartFile multipartFile) {
        List<NepseCompany> nepseCompanyList = new ArrayList<>();
        HSSFWorkbook workbook;
        try {
            workbook = new HSSFWorkbook(multipartFile.getInputStream());
        } catch (Exception e) {
            logger.error("invalid file format {}", e.getMessage());
            throw new ServiceValidationException("File format is not valid");
        }
        HSSFSheet workSheet = workbook.getSheetAt(0);
        System.out.println(workSheet.getLastRowNum());
        int i = 0;
        while (i <= workSheet.getLastRowNum()) {
            HSSFRow row = workSheet.getRow(i++);
            if (i == 1) {
                excelHeaderChecker.checkNepseHeader(row);
            } else if (i > 1) {
                NepseCompany nepseCompany = new NepseCompany();
                if (row.getCell(0).toString() != null) {
                    nepseCompany.setCompanyName(row.getCell(0).getStringCellValue());
                }
                if (row.getCell(1).toString() != null) {
                    nepseCompany.setAmountPerUnit(row.getCell(1).getNumericCellValue());
                }
                if (row.getCell(2).toString() != null) {
                    nepseCompany.setCompanyCode(row.getCell(2).getStringCellValue());
                }
                if (row.getCell(3).toString() != null) {
                    if (!(row.getCell(3).toString().equalsIgnoreCase(ShareType.ORDINARY.toString())
                        || row.getCell(3).toString().equalsIgnoreCase(ShareType.PROMOTER.toString()))) {
                        throw new ServiceValidationException(row.getCell(3).toString()
                            + "is  not valid ShareType on row" + "" + (i - 1));
                    }
                    nepseCompany.setShareType(
                        ShareType.valueOf(row.getCell(3).getStringCellValue().toUpperCase()));
                }
                nepseCompanyList.add(nepseCompany);
            }
        }

        return nepseDataValidator(nepseCompanyList);
    }

    private List<NepseCompany> nepseDataValidator(List<NepseCompany> nepseCompanies) {
        Map<String, NepseCompany> nepseList = new HashMap<>();
        List<NepseCompany> filteredList = new ArrayList<>();
        nepseCompanies.forEach(nepseCompany -> {
            if (nepseCompany.getShareType().equals(ShareType.ORDINARY)) {
                if (nepseList.get(nepseCompany.getCompanyName()) != null
                    && nepseList.get(nepseCompany.getCompanyName()).getShareType()
                    .equals(ShareType.ORDINARY)) {
                    return;
                }
                nepseList
                    .put(nepseCompany.getCompanyName(), nepseCompany);
            } else {
                if (nepseList.get(nepseCompany.getCompanyName()) != null
                    && nepseList.get(nepseCompany.getCompanyName()).getShareType()
                    .equals(ShareType.PROMOTER)) {
                    return;
                }
                nepseList.put(nepseCompany.getCompanyName(), nepseCompany);
            }
        });
        for (String key : nepseList.keySet()) {
            filteredList.add(nepseList.get(key));
        }
        return filteredList;
    }
}

