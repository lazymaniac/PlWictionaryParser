package com.mindmap.jane.wiktionary.tagparser;

import com.mindmap.jane.domain.*;
import com.mindmap.jane.domain.enumeration.CognateNounTypesQualifier;
import com.mindmap.jane.domain.enumeration.CognatePartOfSpeechQualifier;
import com.mindmap.jane.domain.Numeration;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example:
 * 1. == tęskny
 * {{pokrewne}}
 * : {{rzecz}} [[tęsknota]] {{f}}, [[tęsknotka]] {{f}}, [[tęsknica]] {{f}}, [[tęskność]] {{f}}, [[tęskliwość]] {{f}}, [[tęsknienie]] {{n}}, [[utęsknienie]] {{n}}
 * : {{czas}} [[tęsknić]] {{ndk}}, [[zatęsknić]] {{dk}}
 * : {{przysł}} [[tęsknie]], [[tęskno]], [[tęskliwie]]
 * <p>
 * 2. == rygor
 * {{pokrewne}}
 * : {{rzecz}} [[rygoryzm]] {{m}}, [[rygorystyczność]] {{f}}, [[rygorysta]] {{m}}, [[rygorystka]] {{f}}
 * : {{przym}} [[rygorystyczny]]
 * : {{przysł}} [[rygorystycznie]]
 * <p>
 * 3. == nadzieja
 * {{pokrewne}}
 * : {{rzecz}} [[Nadzieja]] {{f}}, [[nadziejność]] {{f}}
 * : {{przym}} [[nadziejny]]
 * : {{przysł}} [[nadziejnie]]
 * : {{czas}} [[dziać]]
 * <p>
 * 4 == współpracownik
 * {{pokrewne}}
 * : {{rzecz}} [[współpraca]] {{f}}, [[współpracowanie]] {{n}}
 * :: {{fż}} [[współpracowniczka]] {{f}}
 * : {{czas}} [[współpracować]] {{ndk}}
 * <p>
 * 5 == jabłoń
 * {{pokrewne}}
 * : {{rzecz}} [[jabłko]] {{n}}, [[jabłecznik]] {{m}}
 * :: {{zdrobn}} [[jabłonka]] {{f}}
 * : {{przym}} [[jabłkowy]], [[jabłoniowy]], [[jabłeczny]]
 */
public class CognatesParserUnitTest {

    CognatesParser cognatesParser = new CognatesParser();

    @Test
    public void shouldParse3PartOfSpeechWithDifferentQualifiers() throws Exception {
        // GIVEN
        WikiUnit wikiUnit = new WikiUnit();
        Meaning meaning = new Meaning(new Numeration((byte) 1,(byte) 1), new Sentence());
        meaning.getPartOfSpeechQualifiers().add("rzeczownik");
        meaning.getPartOfSpeechQualifiers().add("przysłówek");
        meaning.getPartOfSpeechQualifiers().add("czasownik");
        wikiUnit.addMeaning(meaning);

        List<String> testData = new ArrayList<>();
        testData.add("{{pokrewne}}");
        testData.add(": {{rzecz}} [[tęsknota]] {{m}}, [[tęskność]] {{f}}, [[tęsknienie]] {{n}}");
        testData.add(": {{czas}} [[tęsknić]] {{ndk}}, [[zatęsknić]] {{dk}}");
        testData.add(": {{przysł}} [[tęsknie]], [[tęskno]]");


        // WHEN
        cognatesParser.parseCognate(wikiUnit, testData);

        // THEN
        List<Cognate> cognates = wikiUnit.getMeaning(new Numeration((byte) 1, (byte) 1)).getCognates();
        assertFalse(cognates.isEmpty());

        // === rzecz
        Cognate tesknota = cognates.get(0);
        assertEquals(CognateNounTypesQualifier.MESKI, tesknota.getNounTypesQualifier());
        assertEquals(CognatePartOfSpeechQualifier.RZECZOWNIK, tesknota.getPartOfSpeechQualifier());
        assertEquals(new Link("tęsknota"), tesknota.getLink());
        assertTrue(tesknota.getCognateQualifiers().isEmpty());

        Cognate tesknosc = cognates.get(1);
        assertEquals(CognateNounTypesQualifier.ZENSKI, tesknosc.getNounTypesQualifier());
        assertEquals(CognatePartOfSpeechQualifier.RZECZOWNIK, tesknosc.getPartOfSpeechQualifier());
        assertEquals(new Link("tęskność"), tesknosc.getLink());
        assertTrue(tesknosc.getCognateQualifiers().isEmpty());

        Cognate tesknienie = cognates.get(2);
        assertEquals(CognateNounTypesQualifier.NIJAKI, tesknienie.getNounTypesQualifier());
        assertEquals(CognatePartOfSpeechQualifier.RZECZOWNIK, tesknienie.getPartOfSpeechQualifier());
        assertEquals(new Link("tęsknienie"), tesknienie.getLink());
        assertTrue(tesknienie.getCognateQualifiers().isEmpty());


        // === czas

        Cognate tesknic = cognates.get(3);
        assertEquals(null, tesknic.getNounTypesQualifier());
        assertEquals(CognatePartOfSpeechQualifier.CZASOWNIK, tesknic.getPartOfSpeechQualifier());
        assertEquals(new Link("tęsknić"), tesknic.getLink());
        assertTrue(tesknic.getCognateQualifiers().contains("ndk"));

        Cognate zatesknic = cognates.get(4);
        assertEquals(null, zatesknic.getNounTypesQualifier());
        assertEquals(CognatePartOfSpeechQualifier.CZASOWNIK, zatesknic.getPartOfSpeechQualifier());
        assertEquals(new Link("zatęsknić"), zatesknic.getLink());
        assertTrue(zatesknic.getCognateQualifiers().contains("dk"));


        // === przysł
        Cognate tesknie = cognates.get(5);
        assertEquals(null, tesknie.getNounTypesQualifier());
        assertEquals(CognatePartOfSpeechQualifier.PRZYSLOWEK, tesknie.getPartOfSpeechQualifier());
        assertEquals(new Link("tęsknie"), tesknie.getLink());
        assertTrue(tesknie.getCognateQualifiers().isEmpty());

        Cognate teskno = cognates.get(6);
        assertEquals(null, teskno.getNounTypesQualifier());
        assertEquals(CognatePartOfSpeechQualifier.PRZYSLOWEK, teskno.getPartOfSpeechQualifier());
        assertEquals(new Link("tęskno"), teskno.getLink());
        assertTrue(teskno.getCognateQualifiers().isEmpty());
    }
}
