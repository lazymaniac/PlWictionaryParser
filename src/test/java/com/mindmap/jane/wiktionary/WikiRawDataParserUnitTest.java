package com.mindmap.jane.wiktionary;

import com.mindmap.jane.ParserApp;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import com.mindmap.jane.utils.TestDictionaryLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParserApp.class)
public class WikiRawDataParserUnitTest {

    private static final Logger log = LoggerFactory.getLogger(WikiRawDataParserUnitTest.class);

    private final String testInput1 = "test-input1.xml";

    @Autowired
    private WikiRawDataParser wikiRawDataParser;

    @Test
    public void test() {

        Dictionary dictionary = TestDictionaryLoader.getDictionaryFromFile(testInput1);

        wikiRawDataParser.parseRawUnits(dictionary);

        assertFalse(dictionary.getSourceWikiUnits().isEmpty());
    }

}

