package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.GraphCompositeNode;
import oop.diskUsage.file.GraphNode;

import java.nio.file.Path;
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

        List<GraphNode> children = compositeNode.getChildren();
        children.sort(Comparator.createComparator());
        children.stream()
                .filter(node -> node instanceof GraphCompositeNode)
                .map(node -> (GraphCompositeNode) node)
                .forEach(node -> sort(node, sortedDirs));
    }

    private static class Comparator {
        public static java.util.Comparator<GraphNode> createComparator() {

            return (o1, o2) -> {
                if (o1.size() == o2.size()) {
                    return o1.path().getFileName().compareTo(o2.path().getFileName());
                }
                return Long.compare(o2.size(), o1.size());
            };

        }

    }
}