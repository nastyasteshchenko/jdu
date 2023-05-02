package oop.diskUsage;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

//TODO bad tests
public class JduIntegrationTest {

    private static final TempFolder TEMP_FOLDER = new TempFolder();

    @Test
    public void testPrintGraph() {

        int sizeSymLink = TEMP_FOLDER.getStartDirPath().toString().length();

        String expectedOutput = "/dir1 [" + sizeSymLink + " B]\n" +
                "\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" + """
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile1 [0 B]
                \tfile2 [0 B]
                """;

        doTest(expectedOutput, new String[]{TEMP_FOLDER.getStartDirPath().toString()});
    }

    @Test
    public void testPrintGraphWithCertainDepth() {

        String expectedOutput = """
                /dir1 [0 B]
                \t/dir2 [0 B]
                \tfile1 [0 B]
                \tfile2 [0 B]
                """;

        doTest(expectedOutput, new String[]{TEMP_FOLDER.getStartDirPath().toString(), "--depth", "2"});
    }

    @Test
    public void testPrintGraphWithCertainLimit(){

        int sizeSymLink = TEMP_FOLDER.getStartDirPath().toString().length();

        String expectedOutput = "/dir1 [" + sizeSymLink + " B]\n" +
                "\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" + """
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile1 [0 B]
                """;

        doTest(expectedOutput, new String[]{TEMP_FOLDER.getStartDirPath().toString(), "--limit", "2"});
    }

//    @Test
//    public void testWrongArgument(){
//
//        String expectedOutput = "";
//
//        doTest(expectedOutput, new String[]{TEMP_FOLDER.getStartDirPath().toString(), "--limit", "-2"});
//    }

    @Test
    public void testPrintGraphWithCycles() {

        int sizeSymLink = TEMP_FOLDER.getStartDirPath().toString().length();

        String expectedOutput = "/dir1 [" + sizeSymLink + " B]\n" +
                "\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" +
                "\t\t\t/dir1 [" + sizeSymLink + " B]\n" +
                "\t\t\t\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t\t\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" +
                "\t\t\t\t\t\t/dir1 [" + sizeSymLink + " B]\n" +
                "\t\t\t\t\t\t\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t\t\t\t\t\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" + """
                \t\t\t\t\t\t\t\t/dir3 [0 B]
                \t\t\t\t\t\t\tfile1 [0 B]
                \t\t\t\t\t\t\tfile2 [0 B]
                \t\t\t\t\t/dir3 [0 B]
                \t\t\t\t\t\tDasha [0 B]
                \t\t\t\tfile1 [0 B]
                \t\t\t\tfile2 [0 B]
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile1 [0 B]
                \tfile2 [0 B]
                """;

        doTest(expectedOutput, new String[]{TEMP_FOLDER.getStartDirPath().toString(), "-L", "--depth", "9"});
    }

    private void doTest(String expectedOutput, String[] args) {
        StringBuilder output = new StringBuilder();
        Jdu.run(args, output);
        assertEquals(expectedOutput, output.toString());
    }
}