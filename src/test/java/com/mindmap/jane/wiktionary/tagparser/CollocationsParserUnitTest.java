package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.Collocation;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.testutils.WikiUnitUtils;
import com.mindmap.jane.domain.Numeration;
import com.mindmap.jane.wiktionary.numeration.NumerationInterpreter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Szablon: {{kolokacje}}
 * hasło Perseusz
 * {{kolokacje}}
 * : (1.1) [[heros]] Perseusz • [[dzielny]] / [[mężny]] / [[nieustraszony]] / [[odważny]] Perseusz
 * : (2.1) Perseusz [[górować|góruje]] / [[widnieć|widnieje]] [[na]] [[niebo|niebie]] • Perseusz [[być|jest]] [[widoczny]] / [[widzialny]] • [[gwiazdozbiór]] / [[konstelacja]] Perseusza • [[fotografować]] Perseusza • [[podziwiać]] / [[obserwować]] / [[zobaczyć]] Perseusza • [[leżeć]] / [[znajdować się]] [[w]] Perseuszu
 */
public class CollocationsParserUnitTest {

    CollocationsParser collocationsParser = new CollocationsParser(new NumerationInterpreter());

    @Test
    public void shouldParseCollocation() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = WikiUnitUtils.getWikiUnit(3, 1);
        List<String> testInput = new ArrayList<>();
        testInput.add("{{kolokacje}}");
        testInput.add(": (1.1) [[heros]] Perseusz • [[dzielny]] / [[mężny]] / [[nieustraszony]] / [[odważny]] Perseusz");
        testInput.add(": (2.1) Perseusz [[górować|góruje]] / [[widnieć|widnieje]] [[na]] [[niebo|niebie]] • Perseusz [[być|jest]] [[widoczny]] / [[widzialny]]");

        // WHEN
        collocationsParser.parseCollocation(wikiUnit, testInput);

        // THEN
        List<Collocation> collocations = wikiUnit.getMeaning(new Numeration((byte) 1, (byte) 1)).getCollocations();
        List<Collocation> collocations2 = wikiUnit.getMeaning(new Numeration((byte) 2, (byte) 1)).getCollocations();

        assertFalse(collocations.isEmpty());
        assertFalse(collocations2.isEmpty());



    }
}
