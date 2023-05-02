package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public final class SymbolicLinkGraphNode extends GraphCompositeNode {
    private final long size;
    private GraphNode child;

    public SymbolicLinkGraphNode(Path path, long size) {
        super(path);
        this.size = size;
    }

    @Override
    public void addChild(GraphNode child) {
        this.child = child;
    }

    @Override
    public List<GraphNode> getChildren() {
        return child == null ? Collections.emptyList() : Collections.singletonList(child);
    }

    @Override
    public long size() {
        return size;
    }
}