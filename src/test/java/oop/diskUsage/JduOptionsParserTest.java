package oop.diskUsage;

import junit.framework.TestCase;
import org.junit.Test;

public class JduOptionsParserTest {
    @Test
    public void testNoOptions() throws UserInputException {
        String[] userInput = {""};
        JduOptions jduOptions = JduOptionsParser.create(userInput);

        TestCase.assertEquals(jduOptions.depth(), JduOptions.DEFAULT_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.DEFAULT_AMOUNT_OF_FILES);
        TestCase.assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);

    }

    @Test
    public void testWithSymLinkOption() throws UserInputException {
        String[] userInput = {"-L"};
        JduOptions jduOptions = JduOptionsParser.create(userInput);

        TestCase.assertEquals(jduOptions.depth(), JduOptions.DEFAULT_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.DEFAULT_AMOUNT_OF_FILES);
        TestCase.assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithDepthOption() throws UserInputException {
        String[] userInput = {"--depth", "6"};
        JduOptions jduOptions = JduOptionsParser.create(userInput);

        TestCase.assertEquals(jduOptions.depth(), 6);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.DEFAULT_AMOUNT_OF_FILES);
        TestCase.assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithLimitOption() throws UserInputException {
        String[] userInput = {"--limit", "8"};
        JduOptions jduOptions = JduOptionsParser.create(userInput);

        TestCase.assertEquals(jduOptions.depth(), JduOptions.DEFAULT_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithTwoOptions1() throws UserInputException {
        String[] userInput = {"--limit", "8",  "--depth", "7"};
        JduOptions jduOptions = JduOptionsParser.create(userInput);

        TestCase.assertEquals(jduOptions.depth(), 7);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithTwoOptions2() throws UserInputException {
        String[] userInput = {"-L", "--limit", "8"};
        JduOptions jduOptions = JduOptionsParser.create(userInput);

        TestCase.assertEquals(jduOptions.depth(), JduOptions.DEFAULT_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithThreeOption() throws UserInputException {
        String[] userInput = {"--depth", "10", "--limit", "8",  "-L"};
        JduOptions jduOptions = JduOptionsParser.create(userInput);

        TestCase.assertEquals(jduOptions.depth(), 10);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertTrue(jduOptions.passThroughSymLink());
    }
}