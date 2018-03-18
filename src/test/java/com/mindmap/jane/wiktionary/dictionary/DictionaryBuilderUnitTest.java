package com.mindmap.jane.wiktionary.dictionary;

import org.junit.Test;

import static com.mindmap.jane.testutils.DictionaryBuilderTestConfigurator.getDictionaryBuilderForTestFile;
import static org.junit.Assert.*;

public class DictionaryBuilderUnitTest {

    private DictionaryBuilder dictionaryBuilder = getDictionaryBuilderForTestFile("test-data/test-input3.xml");

    @Test
    public void shouldParseFile() throws Exception {
        // GIVEN
        // test file

        // WHEN
        Dictionary dictionary = dictionaryBuilder.buildDictionary();

        // THEN
        assertEquals("Should has 5 units", 5L, dictionary.getRawWikiUnits().size());
    }
}
