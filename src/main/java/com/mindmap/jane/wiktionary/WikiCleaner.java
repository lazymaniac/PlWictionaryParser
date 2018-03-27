package com.mindmap.jane.wiktionary;

import com.codahale.metrics.annotation.Timed;
import com.mindmap.jane.domain.SourceWikiUnit;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
// TODO check if all logic can be done in one loop.
public class WikiCleaner {

    private static final Logger log = LoggerFactory.getLogger(WikiCleaner.class);

    private static final String wymowa = "{{wymowa";
    private static final String uwagi = "{{uwagi";
    private static final String tlumaczenia = "{{tłumaczenia";
    private static final String zrodla = "{{źródła";
    private static final String podobne = "{{podobne";
    private static final String toc = "__TOC__";
    private static final String etymologia = "{{etymologia";
    private static final String dopracowac = "{{dopracować";

    private static final String znaczenia = "{{znaczenia}}";
    private final String asterix = "*";
    private final String tagBegginig = "[[";

    public WikiCleaner() {
    }

    @Timed
    public void clearUnits(Dictionary dictionary) {
        clearForeignLanguages(dictionary);
        clearUselessElements(dictionary);
    }

    /**
     * Removed useless elements to trim overall database size.
     *
     * @param dictionary
     */
    private void clearUselessElements(Dictionary dictionary) {
        Pattern newLinePattern = Pattern.compile("\\n");

        for (SourceWikiUnit sourceWikiUnit : dictionary.getSourceWikiUnits()) {
            String text = sourceWikiUnit.getText();

            String[] lines = newLinePattern.split(text);

            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (line.startsWith(asterix) || line.startsWith(tagBegginig) || line.startsWith(wymowa)
                    || line.startsWith(uwagi) || line.startsWith(tlumaczenia) || line.startsWith(zrodla)
                    || line.startsWith(podobne) || line.startsWith(toc) || line.startsWith(dopracowac)) {
                    continue;
                }
                if (line.startsWith(etymologia)) { // after 'etymologia' tag are translations and comments
                    break;
                }
                sb.append(line).append("\n");
            }

            sourceWikiUnit.setText(sb.toString().trim());
        }
    }

    /**
     * Remove all units in foreign languages.
     *
     * @param dictionary
     */
    private void clearForeignLanguages(Dictionary dictionary) {
        Pattern pattern = Pattern.compile("\\n[\\n]+");

        Set<SourceWikiUnit> empty = new HashSet<>();

        for (SourceWikiUnit sourceWikiUnit : dictionary.getSourceWikiUnits()) {
            String text = sourceWikiUnit.getText().trim();

            String[] separated = pattern.split(text);
            for (String s : separated) {
                if (s.contains("{{język polski}}")) {
                    sourceWikiUnit.setText(s);
                    break;
                } else {
                    sourceWikiUnit.setText("");
                }
            }

            if (!sourceWikiUnit.getText().contains(znaczenia) || StringUtils.isBlank(sourceWikiUnit.getText())) {
                empty.add(sourceWikiUnit);
            }
        }

        log.info("Number of empty raw units: {}", empty.size());
        log.info("Number of units in dictionary: {}", dictionary.getSourceWikiUnits().size());
        dictionary.getSourceWikiUnits().removeAll(empty);
        log.info("Size after removing: {}", dictionary.getSourceWikiUnits().size());
    }


}
