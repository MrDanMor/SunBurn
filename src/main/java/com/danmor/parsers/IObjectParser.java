package com.danmor.parsers;

import com.danmor.utils.SunBurnMode;

public interface IObjectParser {
    default void printTypeMismatch() {}


    default Object tryParse(String toParse, Class<?> type) {
        
        try {
            return parse(toParse, type);
        } catch (Exception e) {
            printTypeMismatch();
        }

        return null;
    }


    private Object parse(String toParse, Class<?> type) {
        if (type == String.class) {
            return toParse;
        }

        if (type == Boolean.class) {
            return Boolean.valueOf(toParse);
        }

        if (type == Integer.class) {
            return Integer.valueOf(toParse);
        }

        if (type == Long.class) {
            return Long.valueOf(toParse);
        }

        if (type == Float.class) {
            return Float.valueOf(toParse);
        }

        if (type == Double.class) {
            return Double.valueOf(toParse);
        }

        if (type == SunBurnMode.class) {
            return SunBurnMode.valueOf(toParse);
        }

        return null;
    }


}
