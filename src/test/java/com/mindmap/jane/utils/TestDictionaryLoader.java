package com.mindmap.jane.utils;

import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import com.mindmap.jane.wiktionary.WikiParser;

import java.io.File;

public class TestDictionaryLoader {

    public static Dictionary getDictionaryFromFile(String testFilename) {
        String testFilePath = new File("test-data/" + testFilename).getAbsolutePath();

        Dictionary dictionary = new Dictionary();

        WikiParser wikiParser = new WikiParser(testFilePath, dictionary);
        wikiParser.parseDocument();

        return dictionary;
    }

}
