package oop.diskUsage;

import org.junit.Test;
import java.io.IOException;
import static junit.framework.TestCase.assertEquals;

public class JduIntegrationTest {

    private static final TempFolder tempFolder = new TempFolder();

    @Test
    public void testPrintGraph() throws IOException {

        int sizeSymLink = tempFolder.getStartDirPath().toString().length();

        String expectedOutput = "/dir1 [" + sizeSymLink + " B]\n" +
                "\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" + """
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile2 [0 B]
                \tfile1 [0 B]
                """;

        doTest(new String[]{tempFolder.getStartDirPath().toString()}, expectedOutput);
    }

    @Test
    public void testPrintGraphWithCertainDepth() throws IOException {

        String expectedOutput = """
                /dir1 [0 B]
                \t/dir2 [0 B]
                \tfile2 [0 B]
                \tfile1 [0 B]
                """;

        doTest(new String[]{tempFolder.getStartDirPath().toString(), "--depth", "2"}, expectedOutput);
    }

    @Test
    public void testPrintGraphWithCertainLimit() throws IOException {

        int sizeSymLink = tempFolder.getStartDirPath().toString().length();

        String expectedOutput = "/dir1 [" + sizeSymLink + " B]\n" +
                "\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" + """
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile2 [0 B]
                """;

        doTest(new String[]{tempFolder.getStartDirPath().toString(), "--limit", "2"}, expectedOutput);
    }

    @Test
    public void testPrintGraphWithCycles() throws IOException {

        int sizeSymLink = tempFolder.getStartDirPath().toString().length();

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
                \t\t\t\t\t\t\tfile2 [0 B]
                \t\t\t\t\t\t\tfile1 [0 B]
                \t\t\t\t\t/dir3 [0 B]
                \t\t\t\t\t\tDasha [0 B]
                \t\t\t\tfile2 [0 B]
                \t\t\t\tfile1 [0 B]
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile2 [0 B]
                \tfile1 [0 B]
                """;

        doTest(new String[]{tempFolder.getStartDirPath().toString(), "-L", "--depth", "9"}, expectedOutput);
    }

    private void doTest(String[] args, String expectedOutput) throws IOException {
        StringBuilder output = new StringBuilder();
        Jdu.run(args, output);
        assertEquals(expectedOutput, output.toString());
    }
}