package oop.diskUsage.file;

import java.nio.file.Path;

public sealed class GraphNode permits RegularFileGraphNode, SymbolicLinkGraphNode, DirectoryGraphNode {

    protected Path path;

    protected long size;

    public Path path(){
        return path;
    }

    public long size() {
        return size;
    }

}