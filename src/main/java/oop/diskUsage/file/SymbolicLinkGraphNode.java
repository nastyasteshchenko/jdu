package oop.diskUsage.file;

import java.nio.file.Path;

public final class SymbolicLinkGraphNode extends GraphNode {
    private GraphNode child;

    public SymbolicLinkGraphNode(Path path, long size) {
        this.path = path;
        this.size = size;
    }

    public void addChild(GraphNode file) {
        child = file;
    }

    public GraphNode getChild() {
        return child;
    }


}
