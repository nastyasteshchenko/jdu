package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class DirectoryTreeNode implements TreeNode {

    private final Path path;
    private final List<TreeNode> children = new ArrayList<>();

    private long size = -1;

    public DirectoryTreeNode(Path path) {
        this.path = path;
    }

    public Path path() {
        return path;
    }

    public void addChild(TreeNode file) {
        children.add(file);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    @Override
    public long size() {

        if (size > -1) {
            return size;
        }

        // TODO handle cycle
        long sum = 0;
        for (TreeNode file : children) {
            sum += file.size();
        }

        size = sum;

        return sum;

    }

}
