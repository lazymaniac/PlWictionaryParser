package com.mindmap.jane.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumerationUtilsUnitTest {

    @Test
    public void shouldReturnNumerationTest() {
        // GIVEN
        String input = ": (1.2) {{test}}";

        // WHEN
        String numeration = NumerationUtils.getNumeration(input);

        // THEN
        assertEquals("(1.2)", numeration);
    }

    @Test
    public void shouldReturnNumeration2Test() {
        // GIVEN
        String input = ": (1-2) {{test,asd}}";

        // WHEN
        String numeration = NumerationUtils.getNumeration(input);

        // THEN
        assertEquals("(1-2)", numeration);
    }

    @Test
    public void shouldReturnNullTest() {
        // GIVEN
        String input = ": () {{testetst}} test";

        // WHEN
        String numeration = NumerationUtils.getNumeration(input);

        // THEN
        assertEquals(null, numeration);
    }

    @Test
    public void shouldRemoveNumeration() throws Exception {
        // GIVEN
        String testInput = ": (1-2) {{test,asd}}";

        // WHEN
        String result = NumerationUtils.removeNumerationReference(testInput);

        // THEN
        assertEquals("{{test,asd}}", result);
    }
}
