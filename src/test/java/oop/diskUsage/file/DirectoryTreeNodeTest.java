package oop.diskUsage.file;

import junit.framework.TestCase;
import org.junit.Test;

import java.nio.file.Paths;

public class DirectoryTreeNodeTest {

    @Test
    public void testSizeOfOneFileDirectory() {
        DirectoryGraphNode startDir = new DirectoryGraphNode(Paths.get("/foo"));
        startDir.addChild(new RegularFileGraphNode(Paths.get("/foo/bar.txt"), 6));

        TestCase.assertEquals(6, startDir.size());
    }
    @Test
    public void testSizeOfDirectoryWithOneDepth() {
        DirectoryGraphNode startDir = new DirectoryGraphNode(Paths.get("/foo"));
        startDir.addChild(new RegularFileGraphNode(Paths.get("/foo/bar.txt"), 176));
        startDir.addChild(new SymbolicLinkGraphNode(Paths.get("/foo/link_to_bar"), 11));
        startDir.addChild(new RegularFileGraphNode(Paths.get("/foo/baz.pdf"), 1009));

        TestCase.assertEquals(1196, startDir.size());
    }

    @Test
    public void testSizeOfDirectoryWithThreeDepth() {
        DirectoryGraphNode startDir = new DirectoryGraphNode(Paths.get("/foo"));
        startDir.addChild(new RegularFileGraphNode(Paths.get("/foo/bar.txt"), 176));

        DirectoryGraphNode secondDir = new DirectoryGraphNode(Paths.get("/foo/dir2"));
        startDir.addChild(secondDir);
        secondDir.addChild(new SymbolicLinkGraphNode(Paths.get("/foo/link_to_bar"), 11));
        secondDir.addChild(new RegularFileGraphNode(Paths.get("/foo/baz.pdf"), 1109));

        DirectoryGraphNode thirdDir = new DirectoryGraphNode(Paths.get("/foo/dir2/dir3"));
        secondDir.addChild(thirdDir);
        thirdDir.addChild(new SymbolicLinkGraphNode(Paths.get("/foo/link_to_baz"), 8));
        thirdDir.addChild(new RegularFileGraphNode(Paths.get("/foo/tuc.doc"), 100));

        TestCase.assertEquals(1404, startDir.size());
    }

    @Test
    public void testSizeOfEmptyDirectory() {
        DirectoryGraphNode startDir = new DirectoryGraphNode(Paths.get("/foo"));

        TestCase.assertEquals(0, startDir.size());
    }

}