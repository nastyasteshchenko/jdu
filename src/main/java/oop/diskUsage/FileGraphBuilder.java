package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.GraphNode;
import oop.diskUsage.file.RegularFileGraphNode;
import oop.diskUsage.file.SymbolicLinkGraphNode;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;

public class FileGraphBuilder {
    private static JduOptions jduOptions;
    private static final HashMap<Path, GraphNode> graphNodes = new HashMap<>();

    public static DirectoryGraphNode build(JduOptions jduOptions) throws IOException {
        FileGraphBuilder.jduOptions = jduOptions;
        return build(new DirectoryGraphNode(jduOptions.getStartDir()), 0);
    }

    private static DirectoryGraphNode build(DirectoryGraphNode startDir, int currentDepth) throws IOException {

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(startDir.path())) {

            for (Path filePath : stream) {

                if (Files.isSymbolicLink(filePath)) {

                    Path pathToChild = Files.readSymbolicLink(filePath);

                    SymbolicLinkGraphNode f = new SymbolicLinkGraphNode(filePath, pathToChild.toString().length());

                    if (graphNodes.containsKey(pathToChild)) {

                        f.addChild(graphNodes.get(pathToChild));

                    } else {

                        GraphNode fChild;

                        if (Files.isDirectory(pathToChild)) {

                            fChild = new DirectoryGraphNode(pathToChild);

                            if (jduOptions.isPassThroughSymLink() && currentDepth < jduOptions.getDepth()) {

                                build((DirectoryGraphNode) fChild, currentDepth + 1);

                                graphNodes.put(pathToChild, fChild);

                            }

                        } else {

                            fChild = new RegularFileGraphNode(pathToChild, Files.size(pathToChild));

                            graphNodes.put(pathToChild, fChild);

                        }

                        f.addChild(fChild);

                    }

                    startDir.addChild(f);

                    graphNodes.put(filePath, f);

                    continue;
                }

                if (Files.isDirectory(filePath)) {

                    GraphNode f;

                    if (graphNodes.containsKey(filePath)) {

                        f = graphNodes.get(filePath);

                    } else {

                        f = new DirectoryGraphNode(filePath);

                        graphNodes.put(filePath, f);

                        build((DirectoryGraphNode) f, currentDepth + 1);

                    }

                    startDir.addChild(f);

                    continue;
                }

                if (Files.isRegularFile(filePath)) {

                    GraphNode f;

                    if (graphNodes.containsKey(filePath)) {

                        f = graphNodes.get(filePath);

                    } else {

                        f = new RegularFileGraphNode(filePath, Files.size(filePath));

                        graphNodes.put(filePath, f);

                    }
                    startDir.addChild(f);
                }
            }
        }

        return startDir;
    }
}
