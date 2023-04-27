package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.GraphNode;
import oop.diskUsage.file.RegularFileGraphNode;
import oop.diskUsage.file.SymbolicLinkGraphNode;
import org.junit.Test;

import java.nio.file.Paths;

public class GraphPrinterTest {


    // TODO please rename, don't forget about the tests on cycles
    @Test
    public void testSimple() {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));

        // TODO create options here
        doTest(null, startDir);
    }

    // TODO reuse in all tests
    private void doTest(JduOptions options, GraphNode root) {
        StringBuilder output = new StringBuilder();
        new FileGraphPrinter(options, output).print(root);
    }

    // TODO prevent duplicates (the same as in GraphNodeSizeTest). Utility class?
    private RegularFileGraphNode createRegularFileNode(String path, long size) {
        return new RegularFileGraphNode(Paths.get(path), size);
    }

    private DirectoryGraphNode createDirectoryNode(String path) {
        return new DirectoryGraphNode(Paths.get(path));

    }

    private SymbolicLinkGraphNode createSymbolicLinkGraphNode(String path, long size) {
        return new SymbolicLinkGraphNode(Paths.get(path), size);

    }

}
