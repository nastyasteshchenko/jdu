package oop.diskUsage;

import oop.diskUsage.file.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

class FileGraphPrinter {
    private final JduOptions jduOptions;
    private final Appendable output;

    FileGraphPrinter(JduOptions jduOptions, Appendable output) {
        this.jduOptions = jduOptions;
        this.output = output;
    }

    /**
     * The purpose of this method is to traverse the filesystem graph recursively and print nodes with the necessary nesting.
     *
     * @param root the root of the filesystem graph to be printed.
     * @throws IOException if an I/O error occurs
     */
    void print(GraphNode root) throws IOException {
        print(root, 0);
    }


    private void print(GraphNode currFile, int depth) throws IOException {

        if (depth > jduOptions.depth() - 1) {
            return;
        }

        addTabs(depth);

        printNode(currFile);

        printChildren(currFile, depth);
    }

    private void printNode(GraphNode node) throws IOException {
        if (node instanceof SymbolicLinkGraphNode) {
            output.append("*");
        } else if (node instanceof DirectoryGraphNode) {
            output.append("/");
        }

        output.append(node.path().getFileName().toString())
                .append(" ")
                .append(FileSizeFormatter.format(node.size()))
                .append("\n");
    }

    private void printChildren(GraphNode currFile, int depth) throws IOException {
        if (!(currFile instanceof GraphCompositeNode compositeNode)) {
            return;
        }

        List<GraphNode> children = compositeNode.getChildren();

        int limitAmountOfFiles = 0;
        for (GraphNode file : children) {

            ++limitAmountOfFiles;
            if (limitAmountOfFiles > jduOptions.limitAmountOfFiles()) {
                return;
            }

            print(file, depth + 1);

        }
    }

    private void addTabs(int count) throws IOException {
        output.append("\t".repeat(count));
    }

    private static class FileSizeFormatter {

        private static final String[] UNITS = new String[]{"B", "kB", "MB", "GB"};
        private static final int KILO = 1000;

        private static String format(long size) {

            DecimalFormat df = new DecimalFormat("#.###");

            for (String unit : UNITS) {
                if (size < KILO) {
                    return "[" + df.format(size) + " " + unit + "]";
                }
                size /= KILO;
            }

            return "[" + df.format(size) + " " + UNITS[UNITS.length - 1] + "]";
        }

    }
}