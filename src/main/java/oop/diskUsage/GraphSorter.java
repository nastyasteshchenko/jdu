package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.SymbolicLinkGraphNode;
import oop.diskUsage.file.GraphNode;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;

public class GraphSorter {

    private static final HashMap<Path, DirectoryGraphNode> sortedDirs = new HashMap<>();

    private static boolean childIsDir(SymbolicLinkGraphNode node) {
        return node.getChild() instanceof DirectoryGraphNode;
    }

    public static void sort(DirectoryGraphNode startDir) {

        if (sortedDirs.containsKey(startDir.path())) {
            return;
        }

        startDir.getChildren().sort(Comparator.comparing(GraphNode::size).reversed());

        sortedDirs.put(startDir.path(), startDir);

        for (GraphNode i : startDir.getChildren()) {

            if (i instanceof DirectoryGraphNode) {
                sort((DirectoryGraphNode) i);
                continue;
            }

            if (i instanceof SymbolicLinkGraphNode && childIsDir((SymbolicLinkGraphNode) i)) {
                sort((DirectoryGraphNode) ((SymbolicLinkGraphNode) i).getChild());
            }
        }

    }

}

