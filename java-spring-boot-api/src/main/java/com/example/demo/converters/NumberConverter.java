package com.example.demo.converters;

import java.util.Objects;

public class NumberConverter {


    public static Double convertToDouble(String strNumber) {
        String number = strNumber.replace(",", ".");

        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        if (Objects.isNull(strNumber)) return false;

        String number = strNumber.replace(",", ".");

        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
