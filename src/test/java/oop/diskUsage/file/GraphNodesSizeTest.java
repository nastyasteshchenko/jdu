package oop.diskUsage.file;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static oop.diskUsage.FilesCreator.*;

public class GraphNodesSizeTest {
    @Test
    public void testSizeOfDirectory1() {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 6));

        assertEquals(6, startDir.size());
    }

    @Test
    public void testSizeOfDirectory2() {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));
        startDir.addChild(createSymbolicLinkGraphNode("/foo/link_to_bar", 11));
        startDir.addChild(createRegularFileNode("/foo/baz.pdf", 1009));

        assertEquals(1196, startDir.size());
    }

    @Test
    public void testSizeOfDirectory3() {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");
        startDir.addChild(createRegularFileNode("/foo/bar.txt", 176));

        DirectoryGraphNode secondDir = createDirectoryNode("/foo/dir2");
        startDir.addChild(secondDir);
        secondDir.addChild(createSymbolicLinkGraphNode("/foo/link_to_bar", 11));
        secondDir.addChild(createRegularFileNode("/foo/baz.pdf", 1109));

        DirectoryGraphNode thirdDir = createDirectoryNode("/foo/dir2/dir3");
        secondDir.addChild(thirdDir);
        thirdDir.addChild(createSymbolicLinkGraphNode("/foo/link_to_baz", 8));
        thirdDir.addChild(createRegularFileNode("/foo/tuc.doc", 100));

        assertEquals(1404, startDir.size());
    }

    @Test
    public void testSizeOfEmptyDirectory() {
        DirectoryGraphNode startDir = createDirectoryNode("/foo");

        assertEquals(0, startDir.size());
    }

}