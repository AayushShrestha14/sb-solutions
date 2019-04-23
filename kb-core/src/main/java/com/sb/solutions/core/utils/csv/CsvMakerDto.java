package com.sb.solutions.core.utils.csv;


import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * Created by Rujan Maharjan on 3/4/2019.
 */
@Component
public class CsvMakerDto {

  private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public CsvMakerDto() {
    }

    public CsvMakerDto(Map<String, Object> map) {
        this.map = map;
    }
}
