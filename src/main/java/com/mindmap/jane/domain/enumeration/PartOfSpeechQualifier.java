package com.mindmap.jane.domain.enumeration;

public enum PartOfSpeechQualifier {

    NOUN("rzecz"),

    VERB("czas"),

    ADJECTIVE("przym"),

    ADVERB("przys"),

    NUMERAL("licz"),

    PRONOUN("zaim");


    PartOfSpeechQualifier(String type) {
        this.type = type;
    }

    private final String type;

    public final String getValue() {
        return type;
    }

    public static PartOfSpeechQualifier enumOf(String text) {
        if (text != null) {
            for (PartOfSpeechQualifier partOfSpeechQualifier : PartOfSpeechQualifier.values()) {
                if (text.equalsIgnoreCase(partOfSpeechQualifier.getValue())) {
                    return partOfSpeechQualifier;
                }
            }
        }
        return null;
    }
}
