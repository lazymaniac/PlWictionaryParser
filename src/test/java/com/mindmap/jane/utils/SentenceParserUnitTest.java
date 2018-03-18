package com.mindmap.jane.utils;

import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Sentence;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;

/**
 * {{przykłady}}
 * : (1.1) ''[[bardzo|Bardzo]] [[podobać się|podobają]] [[ja|mi]] [[podobać się|się]] [[dziewczyna|dziewczyny]] [[z]] [[długi]]mi [[włos]]ami.''
 * <p>
 * {{znaczenia}}
 * ''rzeczownik, rodzaj żeński''
 * : (1.1) {{łódź}} [[biały|białe]] [[pieczywo]] [[o]] [[wydłużyć|wydłużonym]] [[kształt|kształcie]]
 */
public class SentenceParserUnitTest {

    @Test
    public void shouldParseExamplesSentence() throws Exception {
        // GIVEN
        String testInput = ": (1.1) ''[[bardzo|Bardzo]] [[podobać się|podobają]] [[ja|mi]] [[podobać się|się]] [[dziewczyna|dziewczyny]] [[z]] [[długi]]mi [[włos]]ami.''";

        // WHEN
        Sentence sentence = SentenceParser.parseSentence(testInput);

        // THEN
        assertNotNull(sentence);
        assertEquals(testInput, sentence.getOriginalForm());
        List<Link> links = sentence.getLinks();
        assertEquals(8, links.size());
    }

    @Test
    public void shouldParseMeaningSentence() throws Exception {
        // GIVEN
        String testInput = ": (1.1) {{łódź}} [[biały|białe]] [[pieczywo]] [[o]] [[wydłużyć|wydłużonym]] [[kształt|kształcie]]";

        // WHEN
        Sentence sentence = SentenceParser.parseSentence(testInput);

        // THEN
        assertNotNull(sentence);
        assertEquals(testInput, sentence.getOriginalForm());
        List<Link> links = sentence.getLinks();
        assertEquals(5, links.size());
    }

    @Test
    public void shouldReturnEmptyResult() throws Exception {
        // GIVEN
        String testInput = "";

        // WHEN
        Sentence sentence = SentenceParser.parseSentence(testInput);

        // THEN
        assertNull(sentence);
    }
}
