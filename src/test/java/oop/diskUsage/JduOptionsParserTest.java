package oop.diskUsage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class JduOptionsParserTest {

    @Rule // TODO do not use deprecated API
    public ExpectedException thrownUserInputException = ExpectedException.none();

    @Test
    public void testNoOptions() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{""});

        assertEquals(jduOptions.getDepth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.getLimitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);

    }

    @Test
    public void testWithSymLinkOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L"});

        assertEquals(jduOptions.getDepth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.getLimitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithDepthOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "6"});

        assertEquals(jduOptions.getDepth(), 6);
        assertEquals(jduOptions.getLimitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithLimitOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8"});

        assertEquals(jduOptions.getDepth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.getLimitAmountOfFiles(), 8);
        assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithTwoOptions1() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8", "--depth", "7"});

        assertEquals(jduOptions.getDepth(), 7);
        assertEquals(jduOptions.getLimitAmountOfFiles(), 8);
        assertEquals(jduOptions.passThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithTwoOptions2() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L", "--limit", "8"});

        assertEquals(jduOptions.getDepth(), JduOptions.MAX_DEPTH);
        assertEquals(jduOptions.getLimitAmountOfFiles(), 8);
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithThreeOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "-L"});

        assertEquals(jduOptions.getDepth(), 10);
        assertEquals(jduOptions.getLimitAmountOfFiles(), 8);
        assertTrue(jduOptions.passThroughSymLink());
    }

    @Test
    public void testWithDuplicateSymLinkOption() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("double definition of 'L' option");

        JduOptionsParser.parse(new String[]{"-L", "--limit", "8", "-L"});
    }

    @Test
    public void testWithDuplicateDepthOption() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("double definition of 'depth' option");

        JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "--depth", "90"});
    }

    @Test
    public void testWithDuplicateLimitOption() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("double definition of 'limit' option");

        JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "--limit", "8", "-L"});
    }

    @Test
    public void testWithLimitExceededForLimit() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("limit exceeded for option 'limit'");

        JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "800000", "-L"});
    }

    @Test
    public void testWithLimitExceededForDepth() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("limit exceeded for option 'depth'");

        JduOptionsParser.parse(new String[]{"--depth", "100000", "--limit", "8", "-L"});
    }

    @Test
    public void testNoArgumentForLimit() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("option requires an argument -- 'limit'");

        JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "-L"});

    }

    @Test
    public void testNoArgumentForDepth() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("option requires an argument -- 'depth'");

        JduOptionsParser.parse(new String[]{"--depth", "--limit", "8", "-L"});
    }

    @Test
    public void testWrongArgumentForLimit() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("wrong argument for option 'limit'");

        JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "-8", "-L"});
    }

    @Test
    public void testWrongArgumentForDepth() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("wrong argument for option 'depth'");

        JduOptionsParser.parse(new String[]{"--depth", "-10", "--limit", "8", "-L"});
    }

    @Test
    public void testInvalidOption() throws UserInputException {
        thrownUserInputException.expect(UserInputException.class);
        thrownUserInputException.expectMessage("no such option '--lmit'\n\n" + availableOptions);

        JduOptionsParser.parse(new String[]{"--depth", "10", "--lmit", "8", "-L"});
    }

    // TODO remove duplicates (oop.diskUsage.UserInputException.availableOptions)
    private static final String availableOptions = """
            Available options:

            --limit n\tshow n the heaviest files and / or directories
            --depth n\tset recursion depth n
            -L\t\t\tfollow symlinks""";
}