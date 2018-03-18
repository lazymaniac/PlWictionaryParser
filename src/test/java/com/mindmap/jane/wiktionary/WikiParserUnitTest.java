package com.mindmap.jane.wiktionary;

import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;

public class WikiParserUnitTest {

    private WikiParser wikiParser;

    private Dictionary dictionary = new Dictionary();

    @Before
    public void init() {
        String testFilePath = new File("test-data/" + "test-input1.xml").getAbsolutePath();
        wikiParser = new WikiParser(testFilePath, dictionary);
    }

    @Test
    public void testUnitTest() {
        wikiParser.parseDocument();

        assertFalse(dictionary.getRawWikiUnits().isEmpty());
    }


}
