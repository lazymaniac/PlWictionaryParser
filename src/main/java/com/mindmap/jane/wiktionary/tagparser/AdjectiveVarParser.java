package com.mindmap.jane.wiktionary.tagparser;


import com.mindmap.jane.domain.AdjectiveVar;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.wiktionary.generators.AdjectiveVarGenerator;
import com.mindmap.jane.utils.RegexConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Opis parametrów szablonu:
 * <p>
 * {{odmiana-przymiotnik-polski | [1] | [2] | odpowiednik = | ims = }}
 * <p>
 * [1] (opcjonalny) – forma podstawowa przymiotnika; jeżeli nie podano, szablon pobierze tytuł strony
 * [2] (opcjonalny) – stopień wyższy lub słowo brak, jeżeli go nie tworzy
 * odpowiednik – wymagany dla przymiotników niekończących się na -i ani na -y (np. zdrów, świadom, godzien, wart, gotów, żyw, pełen, pewien);
 * należy podać odpowiednik przymiotnika kończący się na -y (np. zdrów: odpowiednik=zdrowy)
 * ims=nie – tylko dla przymiotników kończących się na -ony, które nie pochodzą od imiesłowu biernego (np. czerwony, zielony, słony, wrony);
 * w tych przymiotnikach nie zachodzi wymiana samogłosek o-e w liczbie mnogiej, por. czerwony – czerwoni, doświadczony – doświadczeni
 * <p>
 * Examples:
 * [: (1.1) {{odmiana-przymiotnik-polski|bardziej}}]
 * [: (1.1) {{odmiana-przymiotnik-polski|BRAK}}]
 * [: (1.1) {{odmiana-przymiotnik-polski}}]
 * <p>
 * https://pl.wiktionary.org/wiki/Szablon:odmiana-przymiotnik-polski/opis
 */
@Service
public class AdjectiveVarParser {

    private final static Logger log = LoggerFactory.getLogger(AdjectiveVarParser.class);

    private final AdjectiveVarGenerator adjectiveVarGenerator;

    private int BASE_FORM_GROUP = 2;
    private int HIGHER_FORM_GROUP = 4;
    private int EQUIVALENT_GROUP = 6;
    private int IMS_GROUP = 8;

    private final static String templateRegex = "(" + RegexConsts.NUMERATION + "\\{\\{odmiana-przymiotnik-polski[\\s*\\|]*)+" +
        "([" + RegexConsts.ALPHABET + "\\s\\d]+)*" +
        "([\\s\\|]+)*([" + RegexConsts.ALPHABET + "\\s\\d]+)*" +
        "([\\s\\|]+odpowiednik[\\s]*=[\\s]*([" + RegexConsts.ALPHABET + "\\s\\d]*))*" +
        "([\\s\\|]+ims[\\s]*=*([" + RegexConsts.ALPHABET + "\\s\\d]*))*(}})";

    private final Pattern pattern = Pattern.compile(templateRegex);

    @Autowired
    public AdjectiveVarParser(AdjectiveVarGenerator adjectiveVarGenerator) {
        this.adjectiveVarGenerator = adjectiveVarGenerator;
    }

    public AdjectiveVar parseAdjective(WikiUnit wikiUnit, List<String> dataBlock) {
        if (inputIncorrect(wikiUnit, dataBlock)) return null;

        AdjectiveVar result = null;
        String varietyBlock = dataBlock.get(0);
        Matcher matcher = pattern.matcher(varietyBlock);
        while (matcher.find()) {
            String baseForm = matcher.group(BASE_FORM_GROUP);
            String higherForm = matcher.group(HIGHER_FORM_GROUP);
            if (isNotBlank(baseForm) && isBlank(higherForm)) { // if higher form is empty and base form is not empty then
                higherForm = baseForm;
                baseForm = null;
            }
            String equivalent = matcher.group(EQUIVALENT_GROUP);
            String ims = matcher.group(IMS_GROUP);
            result = adjectiveVarGenerator.generateAdjectiveVar(wikiUnit.getName(), baseForm, higherForm, equivalent, ims);
        }

        return result;
    }

    private boolean inputIncorrect(WikiUnit wikiUnit, List<String> dataBlock) {
        if (dataBlock == null) {
            log.error("Invalid input. Unit name: {}", dataBlock);
            return true;
        }

        if (dataBlock.size() > 1) {
            log.error("Problem parsing dataBlock. Too many elements. Unit name: {}", wikiUnit.getName());
        }
        return false;
    }
}
