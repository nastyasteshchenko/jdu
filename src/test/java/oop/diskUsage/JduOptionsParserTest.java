package oop.diskUsage;

import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JduOptionsParserTest {

    @Rule
    public ExpectedException thrownUserInputException = ExpectedException.none();

    @Test
    public void testNoOptions() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{""});

        TestCase.assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        TestCase.assertEquals(jduOptions.isPassThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);

    }

    @Test
    public void testWithSymLinkOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L"});

        TestCase.assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        TestCase.assertTrue(jduOptions.isPassThroughSymLink());
    }

    @Test
    public void testWithDepthOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "6"});

        TestCase.assertEquals(jduOptions.depth(), 6);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), JduOptions.MAX_AMOUNT_OF_FILES);
        TestCase.assertEquals(jduOptions.isPassThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithLimitOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8"});

        TestCase.assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertEquals(jduOptions.isPassThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithTwoOptions1() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--limit", "8", "--depth", "7"});

        TestCase.assertEquals(jduOptions.depth(), 7);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertEquals(jduOptions.isPassThroughSymLink(), JduOptions.DEFAULT_PASS_THROUGH_SYMLINK);
    }

    @Test
    public void testWithTwoOptions2() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"-L", "--limit", "8"});

        TestCase.assertEquals(jduOptions.depth(), JduOptions.MAX_DEPTH);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertTrue(jduOptions.isPassThroughSymLink());
    }

    @Test
    public void testWithThreeOption() throws UserInputException {
        JduOptions jduOptions = JduOptionsParser.parse(new String[]{"--depth", "10", "--limit", "8", "-L"});

        TestCase.assertEquals(jduOptions.depth(), 10);
        TestCase.assertEquals(jduOptions.limitAmountOfFiles(), 8);
        TestCase.assertTrue(jduOptions.isPassThroughSymLink());
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

    private static final String availableOptions = """
            Available options:

            --limit n\tshow n the heaviest files and / or directories
            --depth n\tset recursion depth n
            -L\t\t\tfollow symlinks""";


}