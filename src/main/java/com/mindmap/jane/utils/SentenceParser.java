package com.mindmap.jane.utils;

import com.mindmap.jane.domain.Link;
import com.mindmap.jane.domain.Sentence;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * {{przykłady}}
 * : (1.1) ''[[bardzo|Bardzo]] [[podobać się|podobają]] [[ja|mi]] [[podobać się|się]] [[dziewczyna|dziewczyny]] [[z]] [[długi]]mi [[włos]]ami.''
 * <p>
 * {{znaczenia}}
 * ''rzeczownik, rodzaj żeński''
 * : (1.1) {{łódź}} [[biały|białe]] [[pieczywo]] [[o]] [[wydłużyć|wydłużonym]] [[kształt|kształcie]]
 * <p>
 * {{frazeologia}}
 * : [[jabłko Adama]] • [[szczyt Adama]] • [[most Adama]] • [[na świętego Adama]] • ([[wywodzić się]]) [[od Adama i Ewy]] • [[wyjaśniać od Adama i Ewy]] • [[w stroju Adama]] • [[żebro Adama]] • [[Y-chromosomalny Adam]] • [[Adam cóż by poradził, gdyby Bóg w raju Ewy nie posadził]] • [[na Adama pięknie, jutrzenka jasna, będzie stodoła ciasna]] • [[na Adama pięknie, zima prędko pęknie]] • [[począwszy od Adama, każdy człowiek kłamie]] • [[Adam i Ewa pokazują, jaki styczeń i luty po nich następują]] • [[na Adama i Ewy, dobre bydłu i plewy]] • [[słota w dzień Adama i Ewy, zabezpiecz od zimna cholewy]] • [[w dzień Adama i Ewy daruj bliźnim gniewy]] • [[każdy Adam znajdzie sobie Ewę]]
 */
public class SentenceParser {

    public static Sentence parseSentence(String input) {
        Sentence result = new Sentence();
        if (StringUtils.isBlank(input)) {
            return null;
        }

        List<Link> links = LinkParser.parseLinks(input, true);
        result.setLinks(links);
        result.setOriginalForm(input);
        return result;
    }
}
