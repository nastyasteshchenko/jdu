package oop.diskUsage.file;

import java.nio.file.Path;

public final class RegularFileGraphNode extends GraphNode {
    public RegularFileGraphNode(Path path, long size) {
        this.path = path;
        this.size = size;
    }

}
