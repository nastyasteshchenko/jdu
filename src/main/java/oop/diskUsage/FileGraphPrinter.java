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
                .append(FileSizeFormatter.convert(node.size()))
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

    //formater
    private static class FileSizeFormatter {

        //long (int)
        private static final long BYTES_IN_KB = (long) Math.pow(10, 3);
        private static final long SIXTH_POWER_OF_TEN = (long) Math.pow(10, 6);
        private static final long NINTH_POWER_OF_TEN = (long) Math.pow(10, 9);
        private static final long TWELFTH_POWER_OF_TEN = (long) Math.pow(10, 12);

        static String convert(long size) {

            DecimalFormat df = new DecimalFormat("#.###");

            //TODO: How to join this case
            if (size < BYTES_IN_KB) {
                return "[" + size + " B]";
            }
            if (size < SIXTH_POWER_OF_TEN) {
                return "[" + df.format(size / BYTES_IN_KB) + " kB]";
            }
            if (size < NINTH_POWER_OF_TEN) {
                return "[" + df.format(size / SIXTH_POWER_OF_TEN) + " MB]";
            }
            if (size < TWELFTH_POWER_OF_TEN) {
                return "[" + df.format(size / NINTH_POWER_OF_TEN) + " GB]";
            }
            return " ";
        }

    }
}