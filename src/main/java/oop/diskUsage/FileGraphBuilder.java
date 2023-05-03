package oop.diskUsage;

import oop.diskUsage.file.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

class FileGraphBuilder {
    private final JduOptions jduOptions;

    FileGraphBuilder(JduOptions jduOptions) {
        this.jduOptions = jduOptions;
    }

    /**
     * Returns the root of the graph, i.e. the node of the starting directory.<br><br>
     * Recursively traverses a file system directory and constructs a corresponding graph-like structure of nodes.
     *
     * @return the root of the graph, i.e. the node of the starting directory.
     * @throws IOException if it is impossible to read the directory stream.
     */
    DirectoryGraphNode build() throws IOException {
        Map<Path, GraphNode> visitedNodes = new HashMap<>();
        build(null, jduOptions.startDir(), 0, visitedNodes);
        return (DirectoryGraphNode) visitedNodes.get(jduOptions.startDir());
    }

    private void build(GraphCompositeNode parent, Path currFile, int depth, Map<Path, GraphNode> visitedNodes) throws IOException {

        if (depth > jduOptions.depth() - 1) {
            return;
        }

        GraphNode currNode = visitedNodes.get(currFile);

        if (currNode != null) {
            parent.addChild(currNode);
            return;
        }

        if (Files.isSymbolicLink(currFile)) {

            Path pathToChild = Files.readSymbolicLink(currFile);
            SymbolicLinkGraphNode symLinkNode = new SymbolicLinkGraphNode(currFile, pathToChild.toString().length());
            visitedNodes.put(currFile, symLinkNode);
            parent.addChild(symLinkNode);

            if (jduOptions.passThroughSymLink()) {
                build(symLinkNode, pathToChild, depth + 1, visitedNodes);
            }

        } else if (Files.isDirectory(currFile)) {

            DirectoryGraphNode dirNode = new DirectoryGraphNode(currFile);
            visitedNodes.put(currFile, dirNode);

            if (parent != null) {
                parent.addChild(dirNode);
            }

            try (DirectoryStream<Path> filesStream = Files.newDirectoryStream(currFile)) {
                for (Path file : filesStream) {
                    build(dirNode, file, depth + 1, visitedNodes);
                }
            }

        } else if (Files.isRegularFile(currFile)) {

            RegularFileGraphNode fileNode = new RegularFileGraphNode(currFile, Files.size(currFile));
            visitedNodes.put(currFile, fileNode);
            parent.addChild(fileNode);
        }
    }
}