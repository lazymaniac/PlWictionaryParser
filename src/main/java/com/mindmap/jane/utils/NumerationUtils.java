package com.mindmap.jane.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Numeration examples:
 * - (1.1)
 * - (1.1-3)
 * - (1.1,3)
 * - (1-3)
 * - (1,3)
 * - (1.1, 2.3)
 * - (1-2, 3.3)
 */
public class NumerationUtils {

    private final static String numerationRegex = "(:\\s)(\\([0-9.\\-,]+\\))";
    private final static String removeNumerationRegex = "(\\([0-9.\\-,]+\\))";
    private final static String numerationStartChar = ":";
    private final static Pattern numerationPattern = Pattern.compile(numerationRegex);
    private final static Pattern removeNumerationPattern = Pattern.compile(removeNumerationRegex);

    public static String getNumeration(String line) {
        Matcher numerationMatcher = numerationPattern.matcher(line);
        String numeration = null;

        while (numerationMatcher.find()) {
            numeration = numerationMatcher.group(2);
        }

        return numeration;
    }

    public static boolean isNumerationLine(String line) {
        return line.startsWith(numerationStartChar);
    }

    public static String removeNumerationReference(String line) {
        Matcher numerationMatcher = removeNumerationPattern.matcher(line);
        return numerationMatcher.replaceAll("").replaceAll(": ", "");
    }

}
