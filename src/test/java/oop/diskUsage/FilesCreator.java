package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.RegularFileGraphNode;
import oop.diskUsage.file.SymbolicLinkGraphNode;

import java.nio.file.Paths;

public class FilesCreator {
    public static RegularFileGraphNode createRegularFileNode(String path, long size) {
        return new RegularFileGraphNode(Paths.get(path), size);
    }

    public static DirectoryGraphNode createDirectoryNode(String path) {
        return new DirectoryGraphNode(Paths.get(path));

    }

    public static SymbolicLinkGraphNode createSymbolicLinkGraphNode(String path, long size) {
        return new SymbolicLinkGraphNode(Paths.get(path), size);

    }
}
