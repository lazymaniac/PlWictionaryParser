package com.mindmap.jane.testutils;

import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Meaning;
import com.mindmap.jane.domain.Sentence;
import com.mindmap.jane.domain.WikiUnit;
import com.mindmap.jane.domain.Numeration;

import java.util.Collections;

public class WikiUnitUtils {

    public static WikiUnit getWikiUnit(int partOfSpeechSize, int importanceSize) {
        WikiUnit wikiUnit = new WikiUnit();
        for (byte i = 1; i < partOfSpeechSize + 1; i++) {
            for (byte j = 1; j < importanceSize + 1; j++) {
                Meaning meaning = new Meaning(new Numeration(i, j), new Sentence(Collections.singletonList(new Link("meaning" + j, null))));
                wikiUnit.addMeaning(meaning);
            }
        }

        return wikiUnit;
    }

}
