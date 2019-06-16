package com.sb.solutions.core.utils.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sb.solutions.core.constant.FilePath;

/**
 * Created by Rujan Maharjan on 3/4/2019.
 */

@Service
public class CsvMaker {

    private List<CsvHeader> mapperToHeader(Map<String, String> header) {
        List<String> tempMapList = new ArrayList<String>(header.values());
        List<CsvHeader> csvHeaders = new ArrayList<>();
        for (String a : tempMapList) {
            CsvHeader csvHeader = new CsvHeader();
            csvHeader.setHeader(a);
            csvHeaders.add(csvHeader);
        }
        return csvHeaders;
    }

    private List<CsvMakerDto> ModelToMapperData(List objects, Map<String, String> header) {
        List<CsvMakerDto> csvMakerDtoList = new ArrayList<>();
        for (Object o : objects) {

            ObjectMapper oMapper = new ObjectMapper();

            Map<String, Object> map = oMapper.convertValue(o, Map.class);

            CsvMakerDto csvMakerDto = new CsvMakerDto();
            Map<String, Object> map1 = new LinkedHashMap<>();
            List<String> headerKey = new ArrayList<>(header.keySet());
            for (String a : headerKey) {
                if (a.contains(",")) {
                    String d = "";

                    ObjectMapper tempMapper = new ObjectMapper();
                    tempMapper
                        .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);
                    List<String> myList = new ArrayList<String>(Arrays.asList(a.split(",")));

                    for (int q = 0; q < myList.size() - 1; q++) {
                        String modelName = myList.get(q).toString();
                        Object obj = map.get(modelName);

                        if (obj instanceof List<?>) {

                            List<?> list = (List<?>) obj;
                            List<String> listmap = new ArrayList<>();

                            for (Object k : list) {
                                ObjectMapper kMapper = new ObjectMapper();
                                Map<String, Object> newMapper = kMapper.convertValue(k, Map.class);
                                d = d + newMapper.get(myList.get(q + 1).toString()).toString() + ","
                                    + "\r\n";
                            }
                            d = d.substring(0, d.length() - 3);
                            map1.put(a, d);
                        } else {

                            ObjectMapper modelMapper = new ObjectMapper();
                            Map<String, Object> newMapper = modelMapper
                                .convertValue(obj, Map.class);
                            try {
                                map1.put(a, newMapper.get(myList.get(q + 1).toString()).toString());
                            } catch (Exception e) {
                            }
                        }


                    }

                } else {
                    map1.put(a, map.get(a));
                }
            }
            csvMakerDto.setMap(map1);
            csvMakerDtoList.add(csvMakerDto);

        }
        return csvMakerDtoList;
    }

    public String csv(String file, Map<String, String> head, List pojo, String uploadDir) {

        List<CsvHeader> csvHeaders = mapperToHeader(head);
        List<CsvMakerDto> csvMakerDtos = ModelToMapperData(pojo, head);
        Workbook wb = new HSSFWorkbook();

        Date date = new Date();
        String filename = "/" + file + date.getTime() + ".xls";

        File dir = new File(FilePath.getOSPath() + uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (OutputStream os = new FileOutputStream(dir + filename)) {
            Sheet sheet = wb.createSheet("New Sheet");
            CellStyle cellStyle = wb.createCellStyle();

            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Font header = wb.createFont();
            header.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle font = wb.createCellStyle();
            font.setFont(header);
            int headerCount = csvHeaders.size();
            Row r = sheet.createRow(0);

            for (int i = 0; i < headerCount; i++) {
                Cell cell = r.createCell(i);
                cell.setCellStyle(font);
                cell.setCellValue(csvHeaders.get(i).getHeader().toString());

            }
            List<String> tempHeaderList = new ArrayList<String>(head.keySet());
            int rowNum = 1;
            for (CsvMakerDto csvMakerDto : csvMakerDtos) {
                Row row = sheet.createRow(rowNum++);

                for (int j = 0; j < tempHeaderList.size(); j++) {
                    Object o = csvMakerDto.getMap().get(tempHeaderList.get(j));
                    row.createCell(j)
                        .setCellValue(o == null ? "-" : o.toString());
                }


            }

            for (int i = 0; i < headerCount; i++) {
                sheet.autoSizeColumn(i);
            }

            wb.write(os);
            wb.close();
        } catch
        (Exception e) {
        }

        return uploadDir + filename;


    }


}
