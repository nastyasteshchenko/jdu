package oop.diskUsage.file;

import java.nio.file.Path;

public sealed abstract class GraphNode permits GraphCompositeNode, RegularFileGraphNode {

    private final Path path;

    GraphNode(Path path) {
        this.path = path;
    }

    public Path path() {
        return path;
    }

    public abstract long size();
}