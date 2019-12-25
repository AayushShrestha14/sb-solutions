package com.sb.solutions.api.nepseCompany.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.nepseCompany.entity.NepseCompany;
import com.sb.solutions.core.enums.ShareType;
import com.sb.solutions.core.utils.csv.ExcelHeaderChecker;
import com.sb.solutions.core.utils.file.FileUploadUtils;

@Component
public class BulkConverter {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
    ExcelHeaderChecker excelHeaderChecker = new ExcelHeaderChecker();

    @SuppressWarnings("resource")
    public List<NepseCompany> parseNepseCompanyFile(MultipartFile multipartFile) {
        List<NepseCompany> nepseCompanyList = new ArrayList<NepseCompany>();
        //  File file = new File("D:\\Book1.xls");
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            workbook = new HSSFWorkbook(multipartFile.getInputStream());
        } catch (Exception e) {
            logger.error("invalid file format {}" + e);
            return null;
        }
        HSSFSheet workSheet = workbook.getSheetAt(0);
        System.out.println(workSheet.getLastRowNum());
        int i = 0;
        while (i <= workSheet.getLastRowNum()) {
            HSSFRow row = workSheet.getRow(i++);
            if (i == 1) {
                if (!excelHeaderChecker.checkNepseHeader(row)) {
                    logger.error("invalid value format");
                    return null;
                }
            } else if (i > 1) {
                NepseCompany nepseCompany = new NepseCompany();
                if (row.getCell(0).toString() != null) {
                    nepseCompany.setCompanyName(row.getCell(0).toString());
                }
                if (row.getCell(1).toString() != null) {
                    nepseCompany.setAmountPerUnit(Double.parseDouble(row.getCell(1).toString()));
                }
                if (row.getCell(2).toString() != null) {
                    nepseCompany.setCompanyCode(row.getCell(2).toString());
                }
                if (row.getCell(3).toString() != null) {
                    nepseCompany
                        .setShareType(ShareType.valueOf(row.getCell(3).toString().toUpperCase()));
                }
                nepseCompanyList.add(nepseCompany);
            }
        }

        return nepseCompanyValidator(nepseCompanyList);
    }

    public List<NepseCompany> nepseCompanyValidator(List<NepseCompany> nepseCompanies) {
        List<NepseCompany> promoterList = new ArrayList<>();
        List<NepseCompany> ordinaryList = new ArrayList<>();
        nepseCompanies.forEach(nepseCompany -> {
            if (nepseCompany.getShareType().equals(ShareType.ORDINARY) ? promoterList
                .add(nepseCompany) : ordinaryList.add(nepseCompany)) {
                ;
            }
        });
        List<NepseCompany> nList = new ArrayList<>();
        nList.addAll(filtorTopList(promoterList));
        nList.addAll(filtorTopList(ordinaryList));
        return nList;
    }

    public List<NepseCompany> filtorTopList(List<NepseCompany> nepseCompanyList) {
        HashMap<String, NepseCompany> map = new HashMap<>();
        nepseCompanyList.forEach(v -> {
            map.put(v.getCompanyName(), v);
        });
        List<NepseCompany> filteredList = new ArrayList<>();
        for (String key : map.keySet()) {
            filteredList.add(map.get(key));
        }
        return filteredList;
    }
}

