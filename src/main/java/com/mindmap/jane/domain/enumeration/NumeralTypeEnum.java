package com.mindmap.jane.domain.enumeration;

/**
 * Typ wyliczeniowny określający typ liczebnika.
 * <p/>
 * Źródło wikipedia.pl
 * W języku polskim istnieją następujące rodzaje liczebników:
 * główne - jeden, dwa, trzy, cztery, pięć, sześć, siedem, osiem, dziewięć, dziesięć, sto, tysiąc, milion
 * porządkowe - pierwszy, setny, tysięczny
 * ułamkowe - ćwierć, pół, półtora, jedna druga, trzy czwarte, cztery piąte
 * zbiorowe - dwoje, troje, czworo, pięcioro, sześcioro, siedmioro, ośmioro, dziewięcioro, dziesięcioro
 * mnożne - podwójny, potrójny, poczwórny
 * nieokreślone - niewiele, kilka, kilkadziesiąt, kilkaset, wiele, trochę, dużo, mało
 * wielorakie - dwojaki, trojaki
 * wielokrotne - trzykroć, dwakroć
 * wielowyrazowe - dwadzieścia dwa, sto dziewięćdziesiąt dwa
 *
 * @author Sebastian Fabisz
 */

public enum NumeralTypeEnum {

    CARDINAL("główny"),
    ORDINAL("porządkowy"),
    FRACTIONAL("ułamkowy"),
    COLLECTIVE("zbiorowy"),
    MULTIPLICATIVE("mnożny"),
    UNKNOWN("nieokreślony"),
    MULTIFARIOUS("wieloraki"),
    MULTIPLE("wielokrotny"),
    MULTIWORD("wielowyrazowy");

    NumeralTypeEnum(String text) {
        this.type = text;
    }

    private final String type;

    public final String getValue() {
        return type;
    }

    public static NumeralTypeEnum enumOf(String text) {
        if (text != null) {
            for (NumeralTypeEnum liczebnikType : NumeralTypeEnum.values()) {
                if (text.equalsIgnoreCase(liczebnikType.getValue())) {
                    return liczebnikType;
                }
            }
        }
        return null;
    }

}
