package com.mindmap.jane.utils;

import org.apache.commons.lang.StringUtils;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isEmpty;

//TODO get rid of this.
public class WikiStringUtils {

    public static String removeNumeration(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return input.replaceAll(input.startsWith(":") ? "(: \\([0-9.\\-,]+\\))" : "\\([0-9.\\-,]+\\)", EMPTY);
    }

    public static String removeWikiRefs(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return input.replaceAll("(<ref>.*</ref>)", EMPTY);
    }

    public static String removeApostrophes(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return input.replaceAll("('')", EMPTY);
    }

    public static String removeAdditionalComment(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return input.replaceAll("(''[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ,.\\s]+'')", EMPTY);
    }

    public static String getStringAfterEqual(String input) {
        if (isEmpty(input)) {
            return input;
        }
        String temp = input.replaceAll("[\\s|]+", EMPTY);
        String[] splited = temp.trim().split("=");
        if (splited.length > 1) {
            return splited[1];
        }
        return EMPTY;
    }

    public static String getStringBeforeEqual(String input) {
        if (isEmpty(input)) {
            return input;
        }
        String temp = input.replaceAll("[\\s|]+", EMPTY);
        String[] splited = temp.split("=");
        return splited[0];
    }

    public static String removeParenthesis(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return input.replaceAll("[{}()\\[\\]]+", EMPTY);
    }

    public static String removePunctuation(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return input.replaceAll("[,.;:'/\']+", EMPTY);
    }

    public static String removeWikiText(String input) {
        if (isEmpty(input)) {
            return input;
        }
        return input.replace("wikipedia|", EMPTY);
    }

    public static String removeReferences(String input) {
        if (isEmpty(input)) {
            return input;
        }
        String noRef = removeWikiRefs(input);
        return removeWikiText(noRef);
    }

}
