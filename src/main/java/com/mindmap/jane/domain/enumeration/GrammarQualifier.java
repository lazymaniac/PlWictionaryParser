package com.mindmap.jane.domain.enumeration;

public enum GrammarQualifier {

    // części mowy odmienne
    RZECZ("rzecz", "rzeczownik"),
    PRZYM("przym", "przymiotnik"),
    CZAS("czas", "czasownik"),
    LICZ("licz", "liczebnik"),
    ZAIM("zaim", "zaimek"),

    // części mowy nieodmienne
    PRZYSL("przysł", "przysłówek"),
    SP("sp", "spójnik"),
    OKRESL("określ", "określnik"),
    PARTK("partyk", "partykuła"),
    PRZECH("przech", "czasownik przechodni"),
    PRZYIM("przyim", "przyimek"),

    // rodzaj
    F("f", "rodzaj żeński"),
    M_OS("m.-os", "męskoosobowy"),
    M("m", "rodzaj męski"),
    MRZ("mrz", "męskorzeczowy"),
    MZW("mzw", "męskozwierzęcy"),
    N("n", "rodzaj nijaki"),
    NIEOS("nieos", "nieosobowy"),
    NM_OS("nm.-os", "niemęskoosobowy"),
    W("w", "rodzaj wspólny"),

    //liczba
    DU("du", "liczba podwójna"),
    BLM("blm", "bez liczby mnogiej"),
    BLP("blp", "bez liczby pojedynczej"),
    LP("lp", "liczba pojedyncza"),
    LM("lm", "liczba mnoga"),
    LM_M("lm m", "liczba mnoga, rodzaj męskoosobowy"),
    LM_NM("lm nm", "liczba mnoga, rodzaj niemęskoosobowy"),


    // aspekty
    DK("dk", "aspekt dokonany"),
    NDK("ndk", "aspekt niedokonany"),

    // czasy
    TER("ter", "czas teraźniejszy"),
    PRZESZ("przesz", "czas przeszły"),
    PRZYSZ("przysz", "czas przyszły"),

    // strony
    STRBR("strbr", "strona bierna"),
    STRCZ("strcz", "strona czynna"),

    // tryby
    ROZK("rozk", "tryb rozkazujący"),
    OZNAJM("oznajm", "tryb oznajmujący"),
    PRZYP("przyp", "tryb przypuszczający"),

    NIEPRZCH("nprzech", "czasownik nieprzechodni"),
    BEZOK("bezok", "bezokolicznik"),
    BEZOSOB("bezosob", "bezosobowy"),
    ZW("żw", "żywotny"),
    ETYM("etym", "etymologia"),
    FORM_SLOW("form. słow", "formant słowotwórczy"),
    IMS("ims", "imiesłów"),
    MIEDZYR("międzyr", "międzyrostek"),
    NIEODM("nieodm", "nieodmienny"),
    NIESTOPN("niestopn", "nie stopniuje się"),
    NZW("nżw", "nieżywotny"),
    ODCZAS("odczas", "odczasownikowy"),
    ODM("odm", "odmienny, odmieniany"),
    ODPRZYM("odprzym", "odprzymiotnikowy"),
    ODRZECZ("odrzecz", "odrzeczownikowy"),
    OS("os", "osoba"),
    POSTP("postp", "postpozycja"),
    PRZECZ("przecz", "przeczenie"),
    PRZEDR("przedr", "przedrostek"),
    PRZYR("przyr", "przyrostek"),
    RODZ("rodz", "rodzajnik"),
    RODZ_NIEOKR("rodz. nieokr", "rodzajnik nieokreślony"),
    RODZ_OKR("rodz. okr", "rodzajnik określony"),
    STOPN("stopn", "stopnie wyższy i najwyższy"),
    TEM_SLOW("tem. słow", "temat słowotwórczy"),
    WAR("war", "wariant"),
    WYR_PRZYIM("wyr. przyim", "wyrażenie przyimkowe");

    private final String template;
    private final String meaning;

    GrammarQualifier(String template, String meaning) {
        this.template = template;
        this.meaning = meaning;
    }
}
