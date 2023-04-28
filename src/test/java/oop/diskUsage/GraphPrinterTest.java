package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.GraphNode;
import oop.diskUsage.file.RegularFileGraphNode;
import oop.diskUsage.file.SymbolicLinkGraphNode;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;
import static oop.diskUsage.FilesCreator.createDirectoryNode;
import static oop.diskUsage.FilesCreator.createRegularFileNode;

public class GraphPrinterTest {


    // TODO please rename, don't forget about the tests on cycles
    @Test
    public void testSimple() throws IOException {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));

        String expectedOutput = """
                /foo [176 B]
                \tbar.txt [176 B]
                """;

        doTest(new JduOptions(10, 10, false, Paths.get("/foo")), startDir, expectedOutput);
    }

    private void doTest(JduOptions options, GraphNode root, String expectedOutput) throws IOException {
        StringBuilder output = new StringBuilder();
        new FileGraphPrinter(options, output).print(root);
        assertEquals(expectedOutput, output.toString());
    }

}