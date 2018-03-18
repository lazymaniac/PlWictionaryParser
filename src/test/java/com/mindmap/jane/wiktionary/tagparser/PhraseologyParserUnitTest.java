package com.mindmap.jane.wiktionary.tagparser;
import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Phraseology;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.testutils.WikiUnitUtils;
import com.mindmap.jane.wiktionary.numeration.Numeration;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * {{frazeologia}}
 * : [[jabłko Adama]] • [[szczyt Adama]] • [[most Adama]] • [[na świętego Adama]] • ([[wywodzić się]]) [[od Adama i Ewy]] • [[wyjaśniać od Adama i Ewy]] • [[w stroju Adama]] • [[żebro Adama]] • [[Y-chromosomalny Adam]] • [[Adam cóż by poradził, gdyby Bóg w raju Ewy nie posadził]] • [[na Adama pięknie, jutrzenka jasna, będzie stodoła ciasna]] • [[na Adama pięknie, zima prędko pęknie]] • [[począwszy od Adama, każdy człowiek kłamie]] • [[Adam i Ewa pokazują, jaki styczeń i luty po nich następują]] • [[na Adama i Ewy, dobre bydłu i plewy]] • [[słota w dzień Adama i Ewy, zabezpiecz od zimna cholewy]] • [[w dzień Adama i Ewy daruj bliźnim gniewy]] • [[każdy Adam znajdzie sobie Ewę]]
 */
public class PhraseologyParserUnitTest {

    PhraseologyParser phraseologyParser = new PhraseologyParser(new NumerationInterpreter());

    @Test
    public void shouldParsePhraseology() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = WikiUnitUtils.getWikiUnit(2, 1);
        List<String> testInput = new ArrayList<>();
        testInput.add("{{frazeologia}}");
        testInput.add(": [[jabłko Adama]] • [[szczyt Adama]] • [[most Adama]]");

        // WHEN
        phraseologyParser.parsePhraseology(wikiUnit, testInput);

        // THEN
        List<Phraseology> phraseology = wikiUnit.getMeaning(new Numeration((byte) 1, (byte) 1)).getPhraseology();
        assertFalse(phraseology.isEmpty());
        assertEquals(new Link("jabłko Adama"), phraseology.get(0).getLink());
        assertEquals(new Link("szczyt Adama"), phraseology.get(1).getLink());
        assertEquals(new Link("most Adama"), phraseology.get(2).getLink());

        List<Phraseology> phraseology1 = wikiUnit.getMeaning(new Numeration((byte) 2, (byte) 1)).getPhraseology();
        assertFalse(phraseology1.isEmpty());
        assertEquals(new Link("jabłko Adama"), phraseology1.get(0).getLink());
        assertEquals(new Link("szczyt Adama"), phraseology1.get(1).getLink());
        assertEquals(new Link("most Adama"), phraseology1.get(2).getLink());

    }
}
