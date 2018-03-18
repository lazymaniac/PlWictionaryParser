package com.mindmap.jane.domain.enumeration;

public enum ShortsEnum {

    EW("ew", "ewentualnie"),
    ZOB("zob", "zobacz"),
    DAW("daw", "dawniej, dawny"),
    ETC("etc", "et cetera"),
    ITD("itd", "i tak dalej"),
    ITP("itp", "i tym podobne, i temu podobne"),
    JEDN_MIAR("jedn. miar", "jednostka miary"),
    JEDN_MONET("jedn. monet", "jednostka monetarna"),
    MONET("monet", "jednostka monetarna"),
    NP("np", "na przykład"),
    DOSL("dosł", "dosłownie"),
    POR("por", "porównaj"),
    PRAWDOP("prawdop", "prawdopodobnie"),
    T("t", "także"),
    TLUM("tłum", "tłumaczenie"),
    TZN("tzn", "to znaczy"),
    TS("ts", "to samo"),
    WLASC("właśc", "właściwie"),
    ZWL("zwł", "zwłaszcza");


    private final String template;
    private final String meaning;

    ShortsEnum(String template, String meaning) {
        this.template = template;
        this.meaning = meaning;
    }
}
