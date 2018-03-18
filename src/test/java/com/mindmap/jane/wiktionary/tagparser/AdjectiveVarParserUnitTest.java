package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.AdjectiveDegreeVar;
import com.mindmap.jane.domain.AdjectiveVar;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.domain.enumeration.AdjectiveDegreeQualifier;
import com.mindmap.jane.wiktionary.WikiRawDataParserUnitTest;
import com.mindmap.jane.wiktionary.generators.AdjectiveVarGenerator;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
public class AdjectiveVarParserUnitTest {

    private static final Logger log = LoggerFactory.getLogger(WikiRawDataParserUnitTest.class);

    private AdjectiveVarParser adjectiveVarParser = new AdjectiveVarParser(new AdjectiveVarGenerator());

    @Test
    @Ignore
    public void emptyInputTest() {

        //GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        List<String> inputData = new ArrayList<>();

        // WHEN
        AdjectiveVar result = adjectiveVarParser.parseAdjective(wikiUnit, inputData);

        // THEN
        assertNull(result);
    }

    @Test
    @Ignore
    public void shouldUseUnitNameAsBaseFormTest() {
        // GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        wikiUnit.setName("szybki");
        List<String> inputData = new ArrayList<>();
        inputData.add(": (1.1) {{odmiana-przymiotnik-polski}}");

        // WHEN
        AdjectiveVar result = adjectiveVarParser.parseAdjective(wikiUnit, inputData);

        // THEN
        log.info("Result of test: " + result);
        assertEquals(1, result.getAdjectiveDegreeVars().size());
        AdjectiveDegreeVar adjectiveDegreeVar = result.getAdjectiveDegreeVars().get(0);
        assertEquals(AdjectiveDegreeQualifier.ZERO, adjectiveDegreeVar.getAdjectiveDegree());
        assertEquals(5, adjectiveDegreeVar.getCasesVars().size());
    }

    @Test
    public void shouldUseUnitNameAsBaseFormAndParam1AsHigherFormTest() {
        // GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        wikiUnit.setName("żółty");
        List<String> inputData = new ArrayList<>();
        inputData.add(": (1.1) {{odmiana-przymiotnik-polski|żółtszy}}");

        // WHEN
        AdjectiveVar result = adjectiveVarParser.parseAdjective(wikiUnit, inputData);

        // THEN
        log.info("Result of test: " + result);

    }

    @Test
    @Ignore
    public void shouldUseParam1AsBaseFormAndParam2AsHigherFormTest() {
        // GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        wikiUnit.setName("żółty");
        List<String> inputData = new ArrayList<>();
        inputData.add(": (1.1) {{odmiana-przymiotnik-polski|żółty|żółtszy}}");

        // WHEN
        AdjectiveVar result = adjectiveVarParser.parseAdjective(wikiUnit, inputData);

        // THEN
        log.info("Result of test: " + result);

    }

    @Test
    @Ignore
    public void shouldUseEquivalentToGenerateVarTest() {
        //TODO implement
    }

    @Test
    @Ignore
    public void shouldUseImsToGenerateVarTest() {
        //TODO implement
    }

    @Test
    @Ignore
    public void shouldFailOnInvalidInput() {
        //TODO implement
    }

}
