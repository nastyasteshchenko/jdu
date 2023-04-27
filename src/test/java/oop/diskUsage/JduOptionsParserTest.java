package oop.diskUsage;

import org.junit.Test;

import static junit.framework.TestCase.*;
import static oop.diskUsage.UserInputException.availableOptions;
import static org.junit.Assert.assertThrows;

public class JduOptionsParserTest {
    @Test
    public void testNoOptions() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{""});

        assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        assertFalse(jduOptions.passThroughSymLink());

    }

    @Test
    public void testWithSymLinkOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L"});

        assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithDepthOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "6"});

        assertEquals(jduOptions.depth(), 6);
        assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        assertFalse(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithLimitOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8"});

        assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.limitAmountOfFiles(), 8);
        assertFalse(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithTwoOptions1() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8", "--depth", "7"});

        assertEquals(jduOptions.depth(), 7);
        assertEquals(jduOptions.limitAmountOfFiles(), 8);
        assertFalse(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithTwoOptions2() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L", "--limit", "8"});

        assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.limitAmountOfFiles(), 8);
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithThreeOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "-L"});

        assertEquals(jduOptions.depth(), 10);
        assertEquals(jduOptions.limitAmountOfFiles(), 8);
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithDuplicateSymLinkOption() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"-L", "--limit", "8", "-L"}));

        assertEquals("double definition of '-L' option", thrown.getMessage());

    }

    @Test
    public void testWithDuplicateDepthOption() {
        Throwable thrown = assertThrows(UserInputException.class, () -> {
            JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "--depth", "90"});
        });

        assertEquals("double definition of 'depth' option", thrown.getMessage());

    }

    @Test
    public void testWithDuplicateLimitOption() {
        Throwable thrown = assertThrows(UserInputException.class, () -> {
            JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "--limit", "8", "-L"});
        });

        assertEquals("double definition of 'limit' option", thrown.getMessage());

    }

    @Test
    public void testNoArgumentForLimit() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "-L"}));

        assertEquals("option requires an argument -- 'limit'\n\n" + availableOptions, thrown.getMessage());

    }

    @Test
    public void testNoArgumentForDepth() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "--limit", "8", "-L"}));

        assertEquals("option requires an argument -- 'depth'\n\n" + availableOptions, thrown.getMessage());
    }

    @Test
    public void testWrongArgumentForLimit() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "-8", "-L"}));

        assertEquals("wrong argument for option 'limit'\n\n" + availableOptions, thrown.getMessage());

    }

    @Test
    public void testWrongArgumentForDepth() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "-10", "--limit", "8", "-L"}));

        assertEquals("wrong argument for option 'depth'\n\n" + availableOptions, thrown.getMessage());
    }

}