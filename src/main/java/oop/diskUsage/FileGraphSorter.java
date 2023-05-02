package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.GraphCompositeNode;
import oop.diskUsage.file.GraphNode;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FileGraphSorter {

    /**
     * fsdfdsf
     *
     * @param startDir
     */
    static void sort(DirectoryGraphNode startDir) {
        Map<Path, GraphCompositeNode> sortedDirs = new HashMap<>();
        sort(startDir, sortedDirs);
    }

    private static void sort(GraphCompositeNode compositeNode, Map<Path, GraphCompositeNode> sortedDirs) {

        GraphCompositeNode graphNode = sortedDirs.putIfAbsent(compositeNode.path(), compositeNode);
        if (graphNode != null) {
            return;
        }

        //TODO: compare names
        List<GraphNode> children = compositeNode.getChildren();
        children.sort(Comparator.comparing(GraphNode::size).reversed());
        children.stream()
                .filter(node -> node instanceof GraphCompositeNode)
                .map(node -> (GraphCompositeNode) node)
                .forEach(node -> sort(node, sortedDirs));
    }
}