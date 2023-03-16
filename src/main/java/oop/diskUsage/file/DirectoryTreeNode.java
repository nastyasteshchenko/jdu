package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class DirectoryTreeNode implements TreeNode {

    private final Path path;
    private final List<TreeNode> children = new ArrayList<>();

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

        long sum = 0;
        for (TreeNode file : children) {
            sum += file.size();
        }

        return sum;

    }

}
