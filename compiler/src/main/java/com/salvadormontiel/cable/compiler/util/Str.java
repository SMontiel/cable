package com.salvadormontiel.cable.compiler.util;

public final class Str {

    /**
     * Convert a string to kebab case.
     *
     * @param value
     * @return
     */
    public static String kebab(String value) {
        return snake(value, "-");
    }

    /**
     * Convert a string to snake case.
     *
     * @param value
     * @return
     */
    public static String snake(String value) {
        return snake(value, "_");
    }

    /**
     * Convert a string to snake case.
     *
     * @param value
     * @param delimiter
     * @return
     */
    public static String snake(String value, String delimiter) {
        if (!isAlpha(value)) {
            value = capitalizeWords(value).replaceAll("\\s+", "");
            value = value.replaceAll("(.)(?=[A-Z])", "$1" + delimiter).toLowerCase();
        }

        return value;
    }

    public static boolean isAlpha(String str) {
        for (char c : str.toCharArray()) if (!isAlpha(c)) return false;

        return true;
    }

    public static boolean isAlpha(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static String capitalizeWords(String str) {
        String[] words = str.split("\\s");
        if (words.length == 1) return str;

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(capitalize(word)).append(" ");
        }

        return sb.toString().trim();
    }

    public static String capitalize(String str) {
        if (str.length() == 0) return "";

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
