package com.mindmap.jane.utils;

import com.mindmap.jane.domain.Link;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mindmap.jane.utils.RegexConsts.ALPHABET;
import static org.apache.commons.lang.StringUtils.*;
import static org.apache.commons.lang.StringUtils.isNotBlank;


/**
 * Linkowanie
 * W przykładzie linkujemy każde słowo, także słowo omawiane w danym haśle (inaczej niż w Wikipedii). Ułatwia to przenoszenie przykładu do innych, użytych w nim słów.
 * <p>
 * hasło długi:
 * niezupełnie dobrze, brak linków
 * {{przykłady}}
 * : (1.1) ''Bardzo podobają mi się dziewczyny z długimi włosami.''
 * <p>
 * dobrze
 * {{przykłady}}
 * : (1.1) ''[[bardzo|Bardzo]] [[podobać się|podobają]] [[ja|mi]] [[podobać się|się]] [[dziewczyna|dziewczyny]] [[z]] [[długi]]mi [[włos]]ami.''
 * <p>
 * Efekt
 * przykłady:
 * (1.1) Bardzo podobają mi się dziewczyny z długimi włosami.
 * przykłady:
 * (1.1) Bardzo podobają mi się dziewczyny z długimi włosami.
 * Warto zwrócić uwagę na użycie kursywy.
 * <p>
 * Jeśli hasło jest obcojęzyczne, to przykład zawsze powinien być przetłumaczony.
 * <p>
 * hasło lock:
 * źle, brak tłumaczenia	dobrze
 * przykłady:
 * (2.1) Michael locks his house every day.
 */
public class LinkParser {

    private static final Logger log = LoggerFactory.getLogger(LinkParser.class);

    private final static String LINK_REGEX = "(\\[\\[)?([" + ALPHABET + "\\s0-9]*)(\\|)?([" + ALPHABET + "\\s0-9]*)?(]])?([" + ALPHABET + "0-9]*)?";
    private final static String SIMPLE_LINK_REGEX = "(\\[\\[)([" + ALPHABET + "\\s0-9\\-]*)(]])";

    private final static int BASE_FORM_INDEX = 2;
    private final static int USED_FROM_INDEX = 4;
    private final static int POSTFIX_INDEX = 6;

    private final static Pattern linkPattern = Pattern.compile(LINK_REGEX);
    private final static Pattern simpleLinkPattern = Pattern.compile(SIMPLE_LINK_REGEX);

    /**
     * Parse links used in Examples.
     * @param input string with link
     * @param withIndex if set to true assign index to every link in result
     * @return parsed array of links
     */
    public static List<Link> parseLinks(String input, boolean withIndex) {
        List<Link> result = new ArrayList<>();

        Matcher linkMatcher = linkPattern.matcher(input);
        boolean anyFound = false;

        int index = 0;
        while (linkMatcher.find()) {
            anyFound = true;
            String baseForm = linkMatcher.group(BASE_FORM_INDEX).trim();
            String usedForm = linkMatcher.group(USED_FROM_INDEX).trim();
            String postfix = linkMatcher.group(POSTFIX_INDEX).trim();

            if (isEmpty(baseForm) && isEmpty(usedForm) && isEmpty(postfix))
                continue;

            Link link;
            if (withIndex) {
                link = buildLinkWithIndex(baseForm, usedForm, postfix, index);
                index++;
            } else {
                link = buildLink(baseForm, usedForm, postfix);
            }

            result.add(link);
        }

        if (!anyFound) {
            log.error("No links found in provided input: {}, withIndex: {}", index, withIndex);
        }

        return result;

    }

    /**
     * Parse simple link used for example in Cognate: [[text]]
     * @param input string with link
     * @return parsed Link
     */
    public static Link parseSimpleLink(String input) {
        Matcher simpleLinkMatcher = simpleLinkPattern.matcher(input);

        while(simpleLinkMatcher.find()) {
            String baseForm = simpleLinkMatcher.group(2);

            if (StringUtils.isNotBlank(baseForm)) {
                return new Link(baseForm);
            }
        }

        return null;
    }

    private static Link buildLinkWithIndex(String baseForm, String usedForm, String postfix, int index) {
        Link link = getLink(baseForm, usedForm, postfix);
        link.setIndex(index);
        return link;
    }

    private static Link buildLink(String baseForm, String usedForm, String postfix) {
        return getLink(baseForm, usedForm, postfix);
    }

    private static Link getLink(String baseForm, String usedForm, String postfix) {
        Link link = new Link();
        link.setBaseForm(baseForm);
        if (isNotBlank(postfix)) {
            link.setUsedForm(baseForm + postfix);
        }

        if (isNotBlank(usedForm)) {
            link.setUsedForm(usedForm);
        }

        if (isBlank(postfix) && isBlank(usedForm)) {
            link.setUsedForm(baseForm);
        }
        return link;
    }


}
