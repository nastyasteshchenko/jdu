package oop.diskUsage.file;

import java.nio.file.Path;

public final class RegularFileGraphNode extends GraphNode {
    private final long size;

    public RegularFileGraphNode(Path path, long size) {
        super(path);
        this.size = size;
    }

    @Override
    public long size() {
        return size;
    }
}