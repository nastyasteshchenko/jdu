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
     * Traverses the filesystem graph recursively and prints nodes with the necessary nesting.
     *
     * @param root the root of the filesystem graph to be printed.
     * @throws IOException if an I/O error occurs
     */
    void print(DirectoryGraphNode root) throws IOException {
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

    private void addTabs(int count) throws IOException {
        output.append("\t".repeat(count));
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

        int amountOfFiles = 0;
        for (GraphNode file : children) {
            ++amountOfFiles;
            if (amountOfFiles > jduOptions.amountOfFiles()) {
                return;
            }

            print(file, depth + 1);
        }
    }

    private static class FileSizeFormatter {

        private static final long BYTES_IN_KB = (long) Math.pow(10, 3);
        private static final long SIXTH_POWER_OF_TEN = (long) Math.pow(10, 6);
        private static final long NINTH_POWER_OF_TEN = (long) Math.pow(10, 9);
        private static final long TWELFTH_POWER_OF_TEN = (long) Math.pow(10, 12);

        static String format(long size) {

            DecimalFormat df = new DecimalFormat("#.###");

            if (size < BYTES_IN_KB || size > TWELFTH_POWER_OF_TEN) {
                return "[" + size + " B]";
            } else if (size < SIXTH_POWER_OF_TEN) {
                return "[" + df.format(size / BYTES_IN_KB) + " kB]";
            } else if (size < NINTH_POWER_OF_TEN) {
                return "[" + df.format(size / SIXTH_POWER_OF_TEN) + " MB]";
            } else {
                return "[" + df.format(size / NINTH_POWER_OF_TEN) + " GB]";
            }

        }

    }

}