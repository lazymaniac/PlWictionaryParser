package com.mindmap.jane.domain.enumeration;

public enum StyleQualifier {

    PEJOR("pejor", "pejoratywne"),
    OFIC("ofic", "oficjalnie"),
    OBRAZ("obraź", "obraźliwy"),
    NEUTR("neutr", "forma neutralna"),
    NDEPR("ndepr", "niedeprecjatywny, forma niedeprecjatywna"),
    LIT("lit", "literacko"),
    IRON("iron", "ironicznie"),
    INFANT("infant", "infantylnie"),
    KAKOF("kakof", "kakofonicznie, kakofoniczny"),
    KIB("kib", "kibicowskie"),
    KOSC("kośc", "kościelny"),
    KSIAZK("książk", "książkowy styl"),
    LEKCEW("lekcew", "lekceważąco, lekceważący"),
    GORNOL("górnol", "górnolotny"),
    GRUB("grub", "grubiański"),
    GRZECZ("grzecz", "zwrot grzecznościowy"),
    HONOR("honor", "honoryfikatywny"),
    EUFEM("eufem", "eufemistycznie"),
    DYSK("dysk", "dyskursywny"),
    ERUD("erud", "erudycyjnie"),
    EKSPR("ekspr", "ekspresywnie, ekspresywny"),
    DEPR("depr", "deprecjatywny, forma deprecjatywna"),
    ZDROB("zdrobn", "zdrobnienie, zdrobniale"),
    ZGRUB("zgrub", "zgrubienie"),
    CHAR("char", "forma charakterystyczna"),
    DZIWEK("dźwięk", "wyraz dźwiękonaśladowczy, onomatopeja"),
    FRAZ("fraz", "frazeologizm, związek frazeologiczny"),
    LUD("lud", "ludowy"),
    MLODZ("młodz", "młodzieżowy"),
    PIESZCZ("pieszcz", "pieszczotliwie, pieszczotliwy"),
    PERYFR("peryfr", "peryfrastyczny, forma peryfrastyczna"),
    PODN("podn", "podniośle, styl podniosły"),
    POET("poet", "poetycki, poetycko"),
    POGARD("pogard", "pogardliwie"),
    POSP("posp", "pospolicie"),
    POT("pot", "potocznie, potoczny"),
    PRZEN("przen", "przenośnia, przenośnie"),
    PRZEST("przest", "przestarzałe, przestarzały"),
    RUB("rub", "rubasznie, rubaszny"),
    RZAD("rzad", "rzadki, rzadko używany"),
    SKR("skr", "skrót, skrótowiec, skrótowo"),
    SLANG("slang", "określenie slangowe"),
    SYMBOL("symbol", "symbolicznie"),
    TRAD("trad", "zapis tradycyjny"),
    NEOL("neol", "neologizm"),
    UCZN("uczn", "uczniowski"),
    UPROSZCZ("uproszcz", "zapis uproszczony"),
    URZ("urz", "urzędowy"),
    WIEZ("więz", "więzienny"),
    WULG("wulg", "wulgaryzm"),
    WYKRZ("wykrz", "wykrzyknik"),
    WSPOLCZ("współcz", "współcześnie"),
    ZART("żart", "żartobliwie"),
    FORMA_MESKA("fm", "forma męska"),
    FORMA_ZENSKA("fż", "forma żeńska");

    private final String template;
    private final String meaning;

    StyleQualifier(String template, String meaning) {
        this.template = template;
        this.meaning = meaning;
    }

}
