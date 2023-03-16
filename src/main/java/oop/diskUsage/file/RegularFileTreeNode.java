package oop.diskUsage.file;

import java.nio.file.Path;

public final class RegularFileTreeNode implements TreeNode {

    private final Path path;
    private final long size;

    public RegularFileTreeNode(Path path, long size) {
        this.path = path;
        this.size = size;
    }

    public Path path() {
        return path;
    }

    @Override
    public long size() {
        return size;
    }


}
