package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class JduIntegrationTest {

    private static final TempFolder tempFolder = new TempFolder();

    @Test
    public void testPrintGraph() throws IOException, UserInputException {


        JduOptions jduOptions = JduOptionsParser.parse(new String[]{tempFolder.getStartDirPath().toString()});

        DirectoryGraphNode root = new FileGraphBuilder(jduOptions).build();

        FileGraphSorter.sort(root);

        int sizeSymLink = tempFolder.getStartDirPath().toString().length();

        String expectedOutput = "/dir1 [" + sizeSymLink + " B]\n" +
                "\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" + """
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile2 [0 B]
                \tfile1 [0 B]
                """;

        assertEquals(new FileGraphPrinter(jduOptions).print(root), expectedOutput);
    }

    @Test
    public void testPrintGraphWithCertainDepth() throws IOException, UserInputException {

        JduOptions jduOptions = JduOptionsParser.parse(new String[]{tempFolder.getStartDirPath().toString(), "--depth", "2"});

        DirectoryGraphNode root = new FileGraphBuilder(jduOptions).build();

        FileGraphSorter.sort(root);


        String expectedOutput = """
                /dir1 [0 B]
                \t/dir2 [0 B]
                \tfile2 [0 B]
                \tfile1 [0 B]
                """;

        assertEquals(new FileGraphPrinter(jduOptions).print(root), expectedOutput);
    }

    @Test
    public void testPrintGraphWithCertainLimit() throws IOException, UserInputException {

        JduOptions jduOptions = JduOptionsParser.parse(new String[]{tempFolder.getStartDirPath().toString(), "--limit", "2"});

        DirectoryGraphNode root = new FileGraphBuilder(jduOptions).build();

        FileGraphSorter.sort(root);

        int sizeSymLink = tempFolder.getStartDirPath().toString().length();

        String expectedOutput = "/dir1 [" + sizeSymLink + " B]\n" +
                "\t/dir2 [" + sizeSymLink + " B]\n" +
                "\t\t*link_to_dir1 [" + sizeSymLink + " B]\n" + """
                \t\t/dir3 [0 B]
                \t\t\tDasha [0 B]
                \tfile2 [0 B]
                """;

        assertEquals(new FileGraphPrinter(jduOptions).print(root), expectedOutput);
    }

    @Test
    public void testPrintGraphWithCycles() throws IOException, UserInputException {

        JduOptions jduOptions = JduOptionsParser.parse(new String[]{tempFolder.getStartDirPath().toString(), "-L", "--depth", "9"});

        DirectoryGraphNode root = new FileGraphBuilder(jduOptions).build();

        FileGraphSorter.sort(root);

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

        assertEquals(new FileGraphPrinter(jduOptions).print(root), expectedOutput);
    }

    // TODO reuse in all tests here
    private void doTest(String[] args, String expectedOutput) throws IOException {
        StringBuilder output = new StringBuilder();
        Jdu.run(args, output);
        // TODO write assert here
    }
}
