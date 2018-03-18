package com.mindmap.jane.domain.enumeration;

public enum CognatePartOfSpeechQualifier {

    RZECZOWNIK("rzecz"),

    CZASOWNIK("czas"),

    PRZYMIOTNIK("przym"),

    PRZYSLOWEK("przysł"),

    PRZYROSTEK("przyr"),

    TEMAT_SLOWOTWORCZY("temsłow"),

    ZAIMEK("zaim"),

    PARTYKULA("partyk"),

    LICZEBIK("licz"),

    IMIESLOW("ims"),

    PRZYIMEK("przyim"),

    WYKRZYKNIK("wykrz"),

    SKROTOWIEC("skr"),

    OKRESLNIK("określ");

    public final String value;

    CognatePartOfSpeechQualifier(String value) {
        this.value = value;
    }

    public static CognatePartOfSpeechQualifier enumOf(String value) {
        for (CognatePartOfSpeechQualifier cognatePartOfSpeechQualifier : CognatePartOfSpeechQualifier.values()) {
            if (cognatePartOfSpeechQualifier.value.equals(value)) {
                return cognatePartOfSpeechQualifier;
            }
        }
        return null;
    }

}
