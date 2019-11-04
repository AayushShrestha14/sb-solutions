package com.sb.solutions.core.utils;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class NepaliNumberConverter {


    public static String englishNumberToNepali(String number) {
        String nepNumber = "";

        List<Character> characterList = number.chars().mapToObj(e -> (char) e)
            .collect(Collectors.toList());

        for (Character c : characterList) {
            if (!c.toString().equalsIgnoreCase(",")) {
                nepNumber = nepNumber + mapper(c.toString());
            } else {
                nepNumber = nepNumber + c.toString();
            }
        }
        nepNumber = nepNumber.replace("null", "");

        return nepNumber;
    }

    public static String mapper(String num) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "१");
        map.put("2", "२");
        map.put("3", "३");
        map.put("4", "४");
        map.put("5", "५");
        map.put("6", "६");
        map.put("7", "७");
        map.put("8", "८");
        map.put("9", "९");
        map.put("0", "0");
        map.put(".", ".");
        map.put(" ", "");

        return map.get(num);
    }

    public static void main(String[] args) {
        int amount = 1234567890;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "NEP"));

        String moneyString = formatter.format(amount);
        System.out.println(englishNumberToNepali(moneyString));
        System.out.println(moneyString);
    }
}
