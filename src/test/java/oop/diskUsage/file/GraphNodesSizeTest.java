package oop.diskUsage.file;

import junit.framework.TestCase;
import org.junit.Test;

import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class GraphNodesSizeTest {

    @Test
    public void testSizeOfOneFileDirectory() {
        DirectoryGraphNode startDir = createDirNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 6));

        assertEquals(6, startDir.size());
    }

    @Test
    public void testSizeOfDirectoryWithOneLevel() {
        DirectoryGraphNode startDir = createDirNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));
        startDir.addChild(createSymLinkNode("/foo/link_to_bar", 11));
        startDir.addChild(createRegularFileNode("/foo/baz.pdf", 1009));

        assertEquals(1196, startDir.size());
    }

    @Test
    public void testSizeOfDirectoryWithThreeLevels() {
        DirectoryGraphNode startDir = createDirNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));

        DirectoryGraphNode secondDir = createDirNode("/foo/dir2");
        startDir.addChild(secondDir);
        secondDir.addChild(createSymLinkNode("/foo/link_to_bar", 11));
        secondDir.addChild(createRegularFileNode("/foo/baz.pdf", 1109));

        DirectoryGraphNode thirdDir = createDirNode("/foo/dir2/dir3");
        secondDir.addChild(thirdDir);
        thirdDir.addChild(createSymLinkNode("/foo/link_to_baz", 8));
        thirdDir.addChild(createRegularFileNode("/foo/tuc.doc", 100));

        assertEquals(1404, startDir.size());
    }

    @Test
    public void testSizeOfEmptyDirectory() {
        DirectoryGraphNode startDir = createDirNode("/foo");

        assertEquals(0, startDir.size());
    }

    private RegularFileGraphNode createRegularFileNode(String path, long size) {
        return new RegularFileGraphNode(Paths.get(path), size);
    }

    private DirectoryGraphNode createDirNode(String path) {
        return new DirectoryGraphNode(Paths.get(path));

    }

    private SymbolicLinkGraphNode createSymLinkNode(String path, long size) {
        return new SymbolicLinkGraphNode(Paths.get(path), size);
    }

}