package com.mindmap.jane.utils;

import com.mindmap.jane.domain.Link;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LinkParserUnitTest {

    @Test
    public void shouldReturn2LinksTest() throws Exception {
        //GIVEN
        String testInput = "[[test|testowy]], [[test]]owa";

        //WHEN
        List<Link> result = LinkParser.parseLinks(testInput, false);

        //THEN
        assertEquals(2, result.size());
        Link link1 = result.get(0);
        assertEquals("test", link1.getBaseForm());
        assertEquals("testowy", link1.getUsedForm());

        Link link2 = result.get(1);
        assertEquals("test", link2.getBaseForm());
        assertEquals("testowa", link2.getUsedForm());
    }

    @Test
    public void shouldParsePlainTestLinks() throws Exception {
        //GIVEN
        String testInput = "testowa, testowy";

        //WHEN
        List<Link> result = LinkParser.parseLinks(testInput, false);

        //THEN
        assertEquals(2, result.size());
        Link link1 = result.get(0);
        assertEquals("testowa", link1.getBaseForm());
        assertEquals("testowa", link1.getUsedForm());

        Link link2 = result.get(1);
        assertEquals("testowy", link2.getBaseForm());
        assertEquals("testowy", link2.getUsedForm());
    }

    @Test
    public void shouldReturnEmptyResult() throws Exception {
        // GIVEN
        String testInput = "";

        // WHEN
        List<Link> links = LinkParser.parseLinks(testInput, false);

        // THEN
        assertTrue(links.isEmpty());
    }

    @Test
    public void shouldParseLinksInProperOrder() throws Exception {
        //GIVEN
        String testInput = "[[test|testowy]], [[test]]owa";

        //WHEN
        List<Link> result = LinkParser.parseLinks(testInput, true);

        //THEN
        assertEquals(2, result.size());
        Link link1 = result.get(0);
        assertEquals("test", link1.getBaseForm());
        assertEquals("testowy", link1.getUsedForm());
        assertEquals(new Integer(0), link1.getIndex());

        Link link2 = result.get(1);
        assertEquals("test", link2.getBaseForm());
        assertEquals("testowa", link2.getUsedForm());
        assertEquals(new Integer(1), link2.getIndex());
    }

    @Test
    public void shouldAssignBaseFormToUsedFormIfPostfxAndUsedFormIsEmpty() throws Exception {
        // GIVEN
        String testInput = "[[test]], [[Kolejny]]";

        // WHEN
        List<Link> result = LinkParser.parseLinks(testInput, true);

        // THEN
        assertEquals(2, result.size());
        Link link1 = result.get(0);
        assertEquals("test", link1.getBaseForm());
        assertEquals("test", link1.getUsedForm());
        assertEquals(new Integer(0), link1.getIndex());

        Link link2 = result.get(1);
        assertEquals("Kolejny", link2.getBaseForm());
        assertEquals("Kolejny", link2.getUsedForm());
        assertEquals(new Integer(1), link2.getIndex());
    }

    @Test
    public void shouldParserLinksAndPlainTextWords() throws Exception {
        // GIVEN
        String testInput = "[[test]], [[Kolejny]], pusty";

        // WHEN
        List<Link> result = LinkParser.parseLinks(testInput, true);

        // THEN
        assertEquals(3, result.size());
        Link link1 = result.get(0);
        assertEquals("test", link1.getBaseForm());
        assertEquals("test", link1.getUsedForm());
        assertEquals(new Integer(0), link1.getIndex());

        Link link2 = result.get(1);
        assertEquals("Kolejny", link2.getBaseForm());
        assertEquals("Kolejny", link2.getUsedForm());
        assertEquals(new Integer(1), link2.getIndex());

        Link link3 = result.get(2);
        assertEquals("pusty", link3.getBaseForm());
        assertEquals("pusty",  link3.getUsedForm());
        assertEquals(new Integer(2), link3.getIndex());
    }
}
