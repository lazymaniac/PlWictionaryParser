package com.mindmap.jane.domain.enumeration;

/**
 * Specify plural or singular.
 */
public enum NumberQualifier {

    /**
     * Liczba pojedyncza.
     */
    SINGULAR("lp"),

    /**
     * Liczba mnoga.
     */
    PLURAL("lm");

    private NumberQualifier(String type) {
        this.type = type;
    }

    private final String type;

    public final String getValue() {
        return type;
    }

    public static NumberQualifier enumOf(String text) {
        if (text != null) {
            for (NumberQualifier b : NumberQualifier.values()) {
                if (text.equalsIgnoreCase(b.getValue())) {
                    return b;
                }
            }
        }
        return null;
    }

}
