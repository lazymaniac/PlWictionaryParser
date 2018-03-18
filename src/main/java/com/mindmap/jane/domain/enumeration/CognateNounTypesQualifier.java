package com.mindmap.jane.domain.enumeration;

public enum CognateNounTypesQualifier {

    MESKI("m"),
    MESKORZECZOWY("mrz"),
    MEZSKOZWIERZECY("mzw"),
    NESKOOSOBOWY("mos"),
    ZENSKI("f"),
    NIJAKI("n"),
    WSPOLNY("w");

    private final String value;

    CognateNounTypesQualifier(String value) {
        this.value = value;
    }

    public static CognateNounTypesQualifier enumOf(String value) {
        for (CognateNounTypesQualifier cognateNounTypesQualifier : CognateNounTypesQualifier.values()) {
            if (cognateNounTypesQualifier.value.equals(value)) {
                return cognateNounTypesQualifier;
            }
        }

        return null;
    }
}
