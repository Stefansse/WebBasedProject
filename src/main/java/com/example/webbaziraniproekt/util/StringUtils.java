package com.example.webbaziraniproekt.util;

public class StringUtils {
    // Method to clean string values by removing unwanted parts
    public static String cleanString(String input) {
        if (input != null) {
            // Remove language tag (@en) and data type suffix (^^)
            int langTagIndex = input.indexOf('@');
            if (langTagIndex != -1) {
                return input.substring(0, langTagIndex).trim();
            }
            int typeSuffixIndex = input.indexOf("^^");
            if (typeSuffixIndex != -1) {
                return input.substring(0, typeSuffixIndex).trim();
            }

        }
        return input; // Return original if no changes
    }
}