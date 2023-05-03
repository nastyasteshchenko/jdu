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
     * Recursively traverses the filesystem graph and sorts the nodes within each filesystem directory.<br><br>
     * Sorting is done in descending order of file size. If the files are of the same size, then sorting occurs in lexico-orthographic order.
     *
     * @param root the root of the filesystem graph to be sorted.
     */
    static void sort(DirectoryGraphNode root) {
        Map<Path, GraphCompositeNode> sortedDirs = new HashMap<>();
        sort(root, sortedDirs);
    }

    private static void sort(GraphCompositeNode compositeNode, Map<Path, GraphCompositeNode> sortedDirs) {

        GraphCompositeNode graphNode = sortedDirs.putIfAbsent(compositeNode.path(), compositeNode);
        if (graphNode != null) {
            return;
        }

        List<GraphNode> children = compositeNode.getChildren();
        children.sort(Comparator.comparing(GraphNode::size).reversed().thenComparing(GraphNode::path));
        children.stream()
                .filter(node -> node instanceof GraphCompositeNode)
                .map(node -> (GraphCompositeNode) node)
                .forEach(node -> sort(node, sortedDirs));
    }

}
