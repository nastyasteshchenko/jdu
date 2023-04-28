package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.GraphNode;
import oop.diskUsage.file.SymbolicLinkGraphNode;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;
import static oop.diskUsage.FilesCreator.*;

public class GraphPrinterTest {


    // TODO please rename, don't forget about the tests on cycles
    @Test
    public void testDirWithOneFile() throws IOException {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));

        String expectedOutput = """
                /foo [176 B]
                \tbar.txt [176 B]
                """;

        doTest(new JduOptions(JduOptions.MAX_DEPTH, JduOptions.MAX_AMOUNT_OF_FILES, false, Paths.get("/foo")), startDir, expectedOutput);
    }

    @Test
    public void testPrintDir() throws IOException {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));
        startDir.addChild(createRegularFileNode("/foo/baz.txt", 80));

        DirectoryGraphNode secondDir = createDirectoryNode("/foo/dir2");
        startDir.addChild(secondDir);
        secondDir.addChild(createRegularFileNode("/foo/dir2/file1", 30));

        SymbolicLinkGraphNode symLink = createSymbolicLinkGraphNode("/foo/link", 4);
        secondDir.addChild(symLink);

        DirectoryGraphNode thirdDir = createDirectoryNode("/foo/dir2/dir3");
        secondDir.addChild(thirdDir);
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file2", 6));
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file3", 7));
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file4", 8));

        String expectedOutput = """
                /foo [311 B]
                \tbar.txt [176 B]
                \tbaz.txt [80 B]
                \t/dir2 [55 B]
                \t\tfile1 [30 B]
                \t\t*link [4 B]
                \t\t/dir3 [21 B]
                \t\t\tfile2 [6 B]
                \t\t\tfile3 [7 B]
                \t\t\tfile4 [8 B]
                """;

        doTest(new JduOptions(JduOptions.MAX_DEPTH, JduOptions.MAX_AMOUNT_OF_FILES, false, Paths.get("/foo")), startDir, expectedOutput);
    }

    @Test
    public void testPrintDirWithPassThroughSymlink() throws IOException {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));
        startDir.addChild(createRegularFileNode("/foo/baz.txt", 80));

        DirectoryGraphNode secondDir = createDirectoryNode("/foo/dir2");
        startDir.addChild(secondDir);
        secondDir.addChild(createRegularFileNode("/foo/dir2/file1", 30));

        SymbolicLinkGraphNode symLink = createSymbolicLinkGraphNode("/foo/link", 4);
        DirectoryGraphNode dir0 = createDirectoryNode("/dir0");
        dir0.addChild(createRegularFileNode("/dir0/file2", 70));
        symLink.addChild(dir0);
        secondDir.addChild(symLink);

        String expectedOutput = """
                /foo [290 B]
                \tbar.txt [176 B]
                \tbaz.txt [80 B]
                \t/dir2 [34 B]
                \t\tfile1 [30 B]
                \t\t*link [4 B]
                \t\t\t/dir0 [70 B]
                \t\t\t\tfile2 [70 B]
                """;

        doTest(new JduOptions(JduOptions.MAX_DEPTH, JduOptions.MAX_AMOUNT_OF_FILES, true, Paths.get("/foo")), startDir, expectedOutput);
    }

    @Test
    public void testPrintDirWithCertainDepth() throws IOException {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));
        startDir.addChild(createRegularFileNode("/foo/baz.txt", 80));

        DirectoryGraphNode secondDir = createDirectoryNode("/foo/dir2");
        startDir.addChild(secondDir);
        secondDir.addChild(createRegularFileNode("/foo/dir2/file1", 30));

        SymbolicLinkGraphNode symLink = createSymbolicLinkGraphNode("/foo/link", 4);
        secondDir.addChild(symLink);

        DirectoryGraphNode thirdDir = createDirectoryNode("/foo/dir2/dir3");
        secondDir.addChild(thirdDir);
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file2", 6));
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file3", 7));
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file4", 8));

        String expectedOutput = """
                /foo [311 B]
                \tbar.txt [176 B]
                \tbaz.txt [80 B]
                \t/dir2 [55 B]
                """;

        doTest(new JduOptions(2, JduOptions.MAX_AMOUNT_OF_FILES, false, Paths.get("/foo")), startDir, expectedOutput);
    }

    @Test
    public void testPrintDirWithCertainLimit() throws IOException {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));
        startDir.addChild(createRegularFileNode("/foo/baz.txt", 80));

        DirectoryGraphNode secondDir = createDirectoryNode("/foo/dir2");
        startDir.addChild(secondDir);
        secondDir.addChild(createRegularFileNode("/foo/dir2/file1", 30));

        SymbolicLinkGraphNode symLink = createSymbolicLinkGraphNode("/foo/link", 4);
        secondDir.addChild(symLink);

        DirectoryGraphNode thirdDir = createDirectoryNode("/foo/dir2/dir3");
        secondDir.addChild(thirdDir);
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file2", 6));
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file3", 7));
        thirdDir.addChild(createRegularFileNode("/foo/dir2/dir3/file4", 8));

        String expectedOutput = """
                /foo [311 B]
                \tbar.txt [176 B]
                \tbaz.txt [80 B]
                """;

        doTest(new JduOptions(JduOptions.MAX_DEPTH, 2, false, Paths.get("/foo")), startDir, expectedOutput);
    }

    @Test
    public void testPrintDirWithCycles() throws IOException {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));
        startDir.addChild(createRegularFileNode("/foo/baz.txt", 80));
        startDir.addChild(createRegularFileNode("/foo/file.txt", 8));

        DirectoryGraphNode secondDir = createDirectoryNode("/foo/dir2");
        startDir.addChild(secondDir);
        secondDir.addChild(createRegularFileNode("/foo/dir2/file1", 30));

        SymbolicLinkGraphNode symLink = createSymbolicLinkGraphNode("/foo/link", 4);
        symLink.addChild(startDir);
        secondDir.addChild(symLink);

        String expectedOutput = """
                /foo [298 B]
                \tbar.txt [176 B]
                \tbaz.txt [80 B]
                \tfile.txt [8 B]
                \t/dir2 [34 B]
                \t\tfile1 [30 B]
                \t\t*link [4 B]
                \t\t\t/foo [298 B]
                \t\t\t\tbar.txt [176 B]
                \t\t\t\tbaz.txt [80 B]
                \t\t\t\tfile.txt [8 B]
                \t\t\t\t/dir2 [34 B]
                \t\t\t\t\tfile1 [30 B]
                \t\t\t\t\t*link [4 B]
                \t\t\t\t\t\t/foo [298 B]
                \t\t\t\t\t\t\tbar.txt [176 B]
                \t\t\t\t\t\t\tbaz.txt [80 B]
                \t\t\t\t\t\t\tfile.txt [8 B]
                \t\t\t\t\t\t\t/dir2 [34 B]
                \t\t\t\t\t\t\t\tfile1 [30 B]
                \t\t\t\t\t\t\t\t*link [4 B]
                \t\t\t\t\t\t\t\t\t/foo [298 B]
                \t\t\t\t\t\t\t\t\t\tbar.txt [176 B]
                \t\t\t\t\t\t\t\t\t\tbaz.txt [80 B]
                \t\t\t\t\t\t\t\t\t\tfile.txt [8 B]
                \t\t\t\t\t\t\t\t\t\t/dir2 [34 B]
                """;

        doTest(new JduOptions(11, JduOptions.MAX_AMOUNT_OF_FILES, true, Paths.get("/foo")), startDir, expectedOutput);
    }

    private void doTest(JduOptions options, GraphNode root, String expectedOutput) throws IOException {
        StringBuilder output = new StringBuilder();
        new FileGraphPrinter(options, output).print(root);
        assertEquals(expectedOutput, output.toString());
    }

}