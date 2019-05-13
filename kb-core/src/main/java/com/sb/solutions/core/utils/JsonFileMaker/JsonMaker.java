package com.sb.solutions.core.utils.JsonFileMaker;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

/**
 * @author Rujan Maharjan on 5/10/2019
 */


public class JsonMaker {

    public void jsonObjectToFile(Object o, String fileType) {
        JSONObject obj = new JSONObject();
        obj.put("Name", "test");
        obj.put("Author", "App Shah");

        JSONArray company = new JSONArray();
        company.add("Compnay: eBay");
        company.add("Compnay: Paypal");
        company.add("Compnay: Google");
        obj.put("Company List", company);

        try (FileWriter file = new FileWriter("E:\\file1.txt")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (Exception e) {
        }
    }

}
