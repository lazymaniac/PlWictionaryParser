package com.mindmap.jane.wiktionary.numeration;

import com.mindmap.jane.domain.*;
import org.junit.Test;

import java.util.List;

import static com.mindmap.jane.testutils.WikiUnitUtils.getWikiUnit;
import static org.junit.Assert.*;

/**
 * Numeration examples:
 *  - (1.1)
 *  - (1.1-3)
 *  - (1.1,3)
 *  - (1-3)
 *  - (1,3)
 *  - (1.1, 2.3)
 *  - (1-2, 3.3)
 */
public class NumerationInterpreterUnitTest {

    private NumerationInterpreter numerationInterpreter = new NumerationInterpreter();

    @Test
    public void shouldReturnNull() throws Exception {
        //GIVEN
        String input = "(1.3)";

        //WHEN
        List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(null, input);

        //THEN
        assertNull(numerations);
    }

    @Test
    public void shouldInterpretSimpleNumeration() throws Exception {
        //GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        String input = "(1.3)";

        //WHEN
        List<Numeration> numerations = numerationInterpreter.parseReferenceNumeration(wikiUnit, input);

        //THEN
        assertEquals(1, numerations.size());
        assertEquals(new Numeration((byte) 1, (byte) 3), numerations.get(0));
    }

    @Test
    public void shouldInterpretRange1to3() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = getWikiUnit(4, 2);
        String input = "(1-3)";

        // WHEN
        List<Numeration> result = numerationInterpreter.parseReferenceNumeration(wikiUnit, input);

        // THEN
        assertEquals(6, result.size());
        assertEquals(new Numeration((byte) 1, (byte) 1), result.get(0));
        assertEquals(new Numeration((byte) 1, (byte) 2), result.get(1));
        assertEquals(new Numeration((byte) 2, (byte) 1), result.get(2));
        assertEquals(new Numeration((byte) 2, (byte) 2), result.get(3));
        assertEquals(new Numeration((byte) 3, (byte) 1), result.get(4));
        assertEquals(new Numeration((byte) 3, (byte) 2), result.get(5));

    }

    @Test
    public void shouldInterpretRangeOfImportance1to4() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = getWikiUnit(2, 4);
        String input = "(2.1-4)";

        // WHEN
        List<Numeration> result = numerationInterpreter.parseReferenceNumeration(wikiUnit, input);

        // THEN
        assertEquals(4, result.size());
        assertEquals(new Numeration((byte) 2, (byte) 1), result.get(0));
        assertEquals(new Numeration((byte) 2, (byte) 2), result.get(1));
        assertEquals(new Numeration((byte) 2, (byte) 3), result.get(2));
        assertEquals(new Numeration((byte) 2, (byte) 4), result.get(3));
    }

    @Test
    public void shouldReturnTwoDirectImportance() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = getWikiUnit(2, 3);
        String input = "(1.1,3)";

        // WHEN
        List<Numeration> result = numerationInterpreter.parseReferenceNumeration(wikiUnit, input);

        // THEN
        assertEquals(2, result.size());
        assertEquals(new Numeration((byte) 1, (byte) 1), result.get(0));
        assertEquals(new Numeration((byte) 1, (byte) 3), result.get(1));
    }

    @Test
    public void shouldReturn2DirectPartOfSpeechNumeration() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = getWikiUnit(3, 2);
        String input = "(1,3)";

        // WHEN
        List<Numeration> result = numerationInterpreter.parseReferenceNumeration(wikiUnit, input);

        // THEN
        assertEquals(4, result.size());
        assertEquals(new Numeration((byte) 1, (byte) 1), result.get(0));
        assertEquals(new Numeration((byte) 1, (byte) 2), result.get(1));
        assertEquals(new Numeration((byte) 3, (byte) 1), result.get(2));
        assertEquals(new Numeration((byte) 3, (byte) 2), result.get(3));
    }

    @Test
    public void shouldReturnNumerationFor2Direct() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = getWikiUnit(2,3);
        String input = "(1.1, 2.3)";

        // WHEN
        List<Numeration> result = numerationInterpreter.parseReferenceNumeration(wikiUnit, input);

        // THEN
        assertEquals(2, result.size());
        assertEquals(new Numeration((byte) 1, (byte) 1), result.get(0));
        assertEquals(new Numeration((byte) 2, (byte) 3), result.get(1));
    }

    @Test
    public void shouldParse2DifferentTypesOfNumeration() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = getWikiUnit(4,2);
        String input = "(1-2, 3.2)";

        // WHEN
        List<Numeration> result = numerationInterpreter.parseReferenceNumeration(wikiUnit, input);

        // THEN
        assertEquals(5, result.size());
        assertEquals(new Numeration((byte) 1, (byte) 1), result.get(0));
        assertEquals(new Numeration((byte) 1, (byte) 2), result.get(1));
        assertEquals(new Numeration((byte) 2, (byte) 1), result.get(2));
        assertEquals(new Numeration((byte) 2, (byte) 2), result.get(3));
        assertEquals(new Numeration((byte) 3, (byte) 2), result.get(4));
    }

}
