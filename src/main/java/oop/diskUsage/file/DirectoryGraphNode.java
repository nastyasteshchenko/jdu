package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class DirectoryGraphNode extends GraphCompositeNode {

    private final List<GraphNode> children = new ArrayList<>();
    private long size = -1;

    public DirectoryGraphNode(Path path) {
        super(path);
    }

    @Override
    public void addChild(GraphNode file) {
        children.add(file);
    }

    @Override
    public List<GraphNode> getChildren() {
        return children;
    }

    @Override
    public long size() {

        if (size > -1) {
            return size;
        }

        long sumChildrenSize = 0;
        for (GraphNode file : children) {
            sumChildrenSize += file.size();
        }

        size = sumChildrenSize;

        return size;

    }

}