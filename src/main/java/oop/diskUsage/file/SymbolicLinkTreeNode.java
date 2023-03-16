package oop.diskUsage.file;

import java.nio.file.Path;

public final class SymbolicLinkTreeNode implements TreeNode {

    private final Path path;
    private final long size;
    private TreeNode child;


    public SymbolicLinkTreeNode(Path path, long size) {
        this.path = path;
        this.size = size;
    }

    public void addChild(TreeNode file) {
        child = file;
    }

    public TreeNode getChild() {
        return child;
    }

    public Path path() {
        return path;
    }

    @Override
    public long size() {
        return size;
    }


}
