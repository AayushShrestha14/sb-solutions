package com.sb.solutions.core.utils.JsonConverter;

import com.google.gson.Gson;
import com.sb.solutions.core.exception.ApiException;

import java.io.BufferedReader;
import java.io.FileReader;


public class JsonConverter {
    private Gson gson = new Gson();

    public String convertToJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T convertToJson(String path, Class<T> T) {
        StringBuffer jsonCustomerData = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonCustomerData.append(line);
            }
            return gson.fromJson(jsonCustomerData.toString(), T);
        } catch (Exception e) {
            throw new ApiException(e.toString());
        }
    }
}
