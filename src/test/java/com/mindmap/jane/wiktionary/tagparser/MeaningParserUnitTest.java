package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Meaning;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * {{znaczenia}}
 * ''rzeczownik, rodzaj męskoosobowy''
 * : (1.1) {{starop}} [[Maciej]]<ref>Jan Miodek, ''Rozmyślajcie nad mową!'', Prószyński i S-ka, Warszawa 1999, s. 151.</ref>
 * <p>
 * {{znaczenia}}
 * ''rzeczownik, rodzaj żeński''
 * : (1.1) {{hist}} ''[[w]] [[starożytny]]m [[Rzym]]ie'': [[pora]] [[rok]]u, [[w]] [[który|której]] [[Słońce]] [[znajdować się|znajduje się]] [[w]] [[gwiazdozbiór|gwiazdozbiorze]] Psa, [[czyli]] [[od]] [[22]] [[czerwiec|czerwca]] [[do]] [[23]] [[sierpień|sierpnia]]
 * : (1.2) {{książk}} [[gorący|najgorętszy]] [[okres]] [[lato|lata]]
 * <p>
 * {{znaczenia}}
 * ''przymiotnik''
 * : (1.1) [[związany]] [[z]] [[powietrze]]m, [[dotyczyć|dotyczący]] [[powietrze|powietrza]]
 * : (1.2) [[taki]], [[który]] [[odbywać|odbywa]] [[się]] [[w]] [[powietrze|powietrzu]], [[na]] [[pewien|pewnej]] [[wysokość|wysokości]]
 */
public class MeaningParserUnitTest {

    private MeaningParser meaningParser = new MeaningParser(new NumerationInterpreter());

    @Test
    public void shouldParseMeaning() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        List<String> testInput = new ArrayList<>();
        testInput.add("{{znaczenia}} ");
        testInput.add("''rzeczownik, rodzaj męskoosobowy''");
        testInput.add(": (1.1) {{starop}} [[Maciej]]<ref>Jan Miodek, ''Rozmyślajcie nad mową!'', Prószyński i S-ka, Warszawa 1999, s. 151.</ref>");

        // WHEN
        meaningParser.parseMeaning(wikiUnit, testInput);

        // THEN
        List<Meaning> meanings = wikiUnit.getMeanings();
        assertEquals(1, meanings.size());
        Meaning result = meanings.get(0);
        assertNotNull(result.getNumeration());
        assertEquals(1, result.getNumeration().getPartOfSpeech());
        assertEquals(1, result.getNumeration().getImportanceIdx());
        assertEquals(1, result.getMeaningQualifiers().size());
        assertEquals("starop", result.getMeaningQualifiers().get(0));
        assertEquals(2, result.getPartOfSpeechQualifiers().size());
        assertEquals("rzeczownik", result.getPartOfSpeechQualifiers().get(0));
        assertEquals("rodzaj męskoosobowy", result.getPartOfSpeechQualifiers().get(1));
        assertNotNull(result.getSentence());
        assertEquals(1, result.getSentence().getLinks().size());
        assertEquals("[[Maciej]]", result.getSentence().getOriginalForm());
    }

    @Test
    public void shouldParse2Meanings() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        List<String> testInput = new ArrayList<>();
        testInput.add("{{znaczenia}} ");
        testInput.add("''rzeczownik, rodzaj żeński''");
        testInput.add(": (1.1) {{hist}} ''[[w]] [[starożytny]]m [[Rzym]]ie'': [[pora]] [[rok]]u, [[w]] [[który|której]] [[Słońce]] [[znajdować się|znajduje się]] [[w]] [[gwiazdozbiór|gwiazdozbiorze]] Psa, [[czyli]] [[od]] [[22]] [[czerwiec|czerwca]] [[do]] [[23]] [[sierpień|sierpnia]]");
        testInput.add(": (1.2) {{książk}} [[gorący|najgorętszy]] [[okres]] [[lato|lata]]");

        // WHEN
        meaningParser.parseMeaning(wikiUnit, testInput);

        // THEN
        List<Meaning> meanings = wikiUnit.getMeanings();
        assertEquals(2, meanings.size());
        Meaning result1 = meanings.get(0);
        assertNotNull(result1.getNumeration());
        assertEquals(1, result1.getNumeration().getPartOfSpeech());
        assertEquals(1, result1.getNumeration().getImportanceIdx());
        assertEquals(1, result1.getMeaningQualifiers().size());
        assertEquals("hist", result1.getMeaningQualifiers().get(0));
        assertEquals(2, result1.getPartOfSpeechQualifiers().size());
        assertEquals("rzeczownik", result1.getPartOfSpeechQualifiers().get(0));
        assertEquals("rodzaj żeński", result1.getPartOfSpeechQualifiers().get(1));
        assertNotNull(result1.getSentence());
        assertEquals(19, result1.getSentence().getLinks().size());
        assertEquals("''[[w]] [[starożytny]]m [[Rzym]]ie''[[pora]] [[rok]]u, [[w]] [[który|której]] [[Słońce]] [[znajdować się|znajduje się]] [[w]] [[gwiazdozbiór|gwiazdozbiorze]] Psa, [[czyli]] [[od]] [[22]] [[czerwiec|czerwca]] [[do]] [[23]] [[sierpień|sierpnia]]", result1.getSentence().getOriginalForm());
    }
}
