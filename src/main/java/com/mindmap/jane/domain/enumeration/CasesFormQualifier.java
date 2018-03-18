package com.mindmap.jane.domain.enumeration;

/**
 * The CasesFormQualifier enumeration.
 */
public enum CasesFormQualifier {

    NOUN_SING("RZECZ_LP"),

    NOUN_PLUR("RZECZ_LM"),

    ADJ_SING_M("PRZYM_LP_M"),

    ADJ_PLUR_F("PRZYM_LP_F"),

    ADJ_SING_N("PRZYM_LP_N"),

    ADJ_PLUR_M("PRZYM_LM_M"),

    ADJ_PLUR_N("PRZYM_LM_N");

    private CasesFormQualifier(String type) {
        this.type = type;
    }

    private final String type;

    public final String getValue() {
        return type;
    }

    public static CasesFormQualifier enumOf(String text) {
        if (text != null) {
            for (CasesFormQualifier b : CasesFormQualifier.values()) {
                if (text.equalsIgnoreCase(b.getValue())) {
                    return b;
                }
            }
        }
        return null;
    }


}
