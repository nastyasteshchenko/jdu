package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.List;

public abstract sealed class GraphCompositeNode extends GraphNode permits DirectoryGraphNode, SymbolicLinkGraphNode {
    public GraphCompositeNode(Path path) {
        super(path);
    }

    public abstract void addChild(GraphNode child);

    public abstract List<GraphNode> getChildren();
}
