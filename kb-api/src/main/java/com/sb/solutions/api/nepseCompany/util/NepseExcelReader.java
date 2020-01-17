package com.sb.solutions.api.nepseCompany.util;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.core.enums.ShareType;
import com.sb.solutions.core.exception.ServiceValidationException;
import com.sb.solutions.core.utils.file.FileUploadUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Sunil Babu Shrestha on 1/17/2020
 * this is utility to read excel load for nepse company
 */
public class NepseExcelReader {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
    private static final int COMPANY_NAME_COLUMN_POS = 0;
    private static final int SHARE_AMOUNT_COLUMN_POS = 1;
    private static final int COMPANY_CODE_COLUMN_POS = 2;
    private static final int SHARE_TYPE_COLUMN_POS = 3;

    private NepseExcelReader() {
    }

    public static List<NepseCompany> parseNepseCompanyFile(MultipartFile multipartFile) {
        List<NepseCompany> nepseCompanyList = new ArrayList<>();
        Workbook wb = null;
        String excelFileName = multipartFile.getOriginalFilename();

        try {
            if (excelFileName.contains(".xlsx")) {
                wb = new XSSFWorkbook(multipartFile.getInputStream());
            } else {
                wb = new HSSFWorkbook(multipartFile.getInputStream());
            }

            Sheet workSheet = wb.getSheetAt(0);
            IntStream.rangeClosed(1, workSheet.getLastRowNum()).forEach(value -> {
                Row row = workSheet.getRow(value);
                NepseCompany nepseCompany = new NepseCompany();

                if (row.getCell(COMPANY_NAME_COLUMN_POS).toString() != null) {
                    nepseCompany.setCompanyName(row.getCell(COMPANY_NAME_COLUMN_POS).getStringCellValue());
                }
                if (row.getCell(SHARE_AMOUNT_COLUMN_POS).toString() != null) {
                    nepseCompany.setAmountPerUnit(row.getCell(SHARE_AMOUNT_COLUMN_POS).getNumericCellValue());
                }
                if (row.getCell(COMPANY_CODE_COLUMN_POS).toString() != null) {
                    nepseCompany.setCompanyCode(row.getCell(COMPANY_CODE_COLUMN_POS).toString());
                }
                if (row.getCell(SHARE_TYPE_COLUMN_POS).toString() != null) {
                    if (!(row.getCell(SHARE_TYPE_COLUMN_POS).toString().trim().equalsIgnoreCase(ShareType.ORDINARY.toString())
                            || row.getCell(SHARE_TYPE_COLUMN_POS).toString().trim().equalsIgnoreCase(ShareType.PROMOTER.toString()))) {
                        throw new ServiceValidationException(row.getCell(SHARE_TYPE_COLUMN_POS).toString() + " "
                                + "is  not valid ShareType on row" + " " + (value - 1));
                    }
                    nepseCompany.setShareType(
                            ShareType.valueOf(row.getCell(3).getStringCellValue().toUpperCase()));
                }
                nepseCompanyList.add(nepseCompany);
            });
        } catch (Exception e) {
            logger.error("invalid file format {}", e.getMessage());
            throw new ServiceValidationException("File format is not valid");
        } finally {
            if (null != wb) {
                try {
                    wb.close();
                } catch (IOException e) {
                    logger.error("Failed to close poi workbook {}", e);
                }
            }
        }
        return nepseCompanyList;
    }
}

