package oop.diskUsage;

import org.junit.Test;

import static junit.framework.TestCase.*;
import static oop.diskUsage.JduOptions.Builder.*;
import static oop.diskUsage.UserInputException.availableOptions;
import static org.junit.Assert.assertThrows;

public class JduOptionsParserTest {
    @Test
    public void testNoOptions() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{""});

        assertEquals(MAX_DEPTH, jduOptions.depth());
        assertEquals(MAX_AMOUNT_OF_FILES, jduOptions.limitAmountOfFiles());
        assertFalse(jduOptions.passThroughSymLink());

    }

    @Test
    public void testSymLinkOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L"});

        assertEquals(MAX_DEPTH, jduOptions.depth());
        assertEquals(MAX_AMOUNT_OF_FILES, jduOptions.limitAmountOfFiles());
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testDepthOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "6"});

        assertEquals(6, jduOptions.depth());
        assertEquals(MAX_AMOUNT_OF_FILES, jduOptions.limitAmountOfFiles());
        assertFalse(jduOptions.passThroughSymLink());
    }

    @Test
    public void testLimitOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8"});

        assertEquals(MAX_DEPTH, jduOptions.depth());
        assertEquals(8, jduOptions.limitAmountOfFiles());
        assertFalse(jduOptions.passThroughSymLink());
    }

    @Test
    public void testTwoOptions1() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8", "--depth", "7"});

        assertEquals(7, jduOptions.depth());
        assertEquals(8, jduOptions.limitAmountOfFiles());
        assertFalse(jduOptions.passThroughSymLink());
    }

    @Test
    public void testTwoOptions2() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L", "--limit", "8"});

        assertEquals(MAX_DEPTH, jduOptions.depth());
        assertEquals(8, jduOptions.limitAmountOfFiles());
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testThreeOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "-L"});

        assertEquals(10, jduOptions.depth());
        assertEquals(8, jduOptions.limitAmountOfFiles());
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testDuplicateSymLinkOption() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"-L", "--limit", "8", "-L"}));

        assertEquals("double definition of '-L' option", thrown.getMessage());

    }

    @Test
    public void testDuplicateDepthOption() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "--depth", "90"}));

        assertEquals("double definition of 'depth' option", thrown.getMessage());

    }

    @Test
    public void testDuplicateLimitOption() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "--limit", "8", "-L"}));

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
    public void testWrongArgumentForLimit1() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "-8", "-L"}));

        assertEquals("wrong argument for option 'limit'\n\nPossible values from " + MIN_AMOUNT_OF_FILES + " to " + MAX_AMOUNT_OF_FILES, thrown.getMessage());

    }

    @Test
    public void testWrongArgumentForLimit2() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8000", "-L"}));

        assertEquals("wrong argument for option 'limit'\n\nPossible values from " + MIN_AMOUNT_OF_FILES + " to " + MAX_AMOUNT_OF_FILES, thrown.getMessage());

    }

    @Test
    public void testWrongArgumentForDepth1() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "-10", "--limit", "8", "-L"}));

        assertEquals("wrong argument for option 'depth'\n\nPossible values from " + MIN_DEPTH + " to " + MAX_DEPTH, thrown.getMessage());
    }

    @Test
    public void testWrongArgumentForDepth2() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "8000", "--limit", "8", "-L"}));

        assertEquals("wrong argument for option 'depth'\n\nPossible values from " + MIN_DEPTH + " to " + MAX_DEPTH, thrown.getMessage());
    }

    @Test
    public void testNoDirectory() {
        Throwable thrown = assertThrows(UserInputException.class, () ->
                JduOptionsParser.parse(new String[]{"--depth", "80", "fhfhhfhff", "--limit", "8", "-L"}));

        assertEquals("cannot access 'fhfhhfhff': No such file or directory\n\n" + availableOptions, thrown.getMessage());
    }

}