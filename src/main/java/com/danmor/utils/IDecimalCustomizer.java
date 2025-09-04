package com.danmor.utils;

import java.text.DecimalFormat;

public interface IDecimalCustomizer {
    default double roundToDigits(double value, int digits) {
        if (digits < 0) return value;
        if (value == 0) return 0;

        double scale = Math.pow(10, digits);
        double newValue = Math.round(value * scale) / scale;

        if (Math.abs(newValue) < 1/scale) {
            return Math.signum(newValue) * 1/scale;
        }
        
        return newValue;
    }

    default String getFormattedFloat(double value, int digits) {
        if (digits < 0) digits = 0;
        
        String string = "#.";
        for (int i = 0; i < digits; i++) {
            string = string + "#";
        }

        DecimalFormat format = new DecimalFormat(string);
        return format.format(value);
    }
}
