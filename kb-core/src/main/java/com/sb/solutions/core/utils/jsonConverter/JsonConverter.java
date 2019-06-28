package com.sb.solutions.core.utils.jsonConverter;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;

import com.sb.solutions.core.exception.ServiceValidationException;

public class JsonConverter {

    private final Gson gson = new Gson();

    public String convertToJson(Object object) {
        return gson.toJson(object);
    }

    public <T> T convertToJson(String path, Class<T> clazz) {
        final StringBuilder jsonCustomerData = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonCustomerData.append(line);
            }
            return gson.fromJson(jsonCustomerData.toString(), clazz);
        } catch (Exception e) {
            throw new ServiceValidationException(e.toString());
        }
    }
}
