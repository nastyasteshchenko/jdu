package oop.diskUsage;

import oop.diskUsage.file.*;

import java.util.List;

class FileGraphPrinter {
    private final JduOptions jduOptions;
    private final Appendable output;

    public FileGraphPrinter(JduOptions jduOptions, Appendable output) {
        this.jduOptions = jduOptions;
        this.output = output;
    }

    void print(GraphNode root) {
        // TODO implement me, reuse new field output everywhere, remove print method below
    }

    public String print(GraphNode root) {
        StringBuilder output = new StringBuilder();
        print(root, 0, output);
        System.out.println(output);
        return output.toString();
    }

    private void print(GraphNode currFile, int depth, StringBuilder output) {

        if (depth > jduOptions.depth() - 1) {
            return;
        }

        addTabs(output, depth);

        if (currFile instanceof GraphCompositeNode) {

            if (currFile instanceof SymbolicLinkGraphNode) {

                output.append("*");
            } else {

                output.append("/");
            }

            output.append(currFile.path().getFileName())
                    .append(" ")
                    .append(FileSizeConverter.print(currFile.size()))
                    .append("\n");

            List<GraphNode> children = ((GraphCompositeNode) currFile).getChildren();

            int limitAmountOfFiles = 0;
            for (GraphNode file : children) {

                if (file != null) {

                    ++limitAmountOfFiles;
                    if (limitAmountOfFiles > jduOptions.limitAmountOfFiles()) {
                        return;
                    }

                    print(file, depth + 1, output);

                }
            }

        } else if (currFile instanceof RegularFileGraphNode) {

            output.append(currFile.path().getFileName())
                    .append(" ")
                    .append(FileSizeConverter.print(currFile.size()))
                    .append("\n");

        }
    }

    private static void addTabs(StringBuilder string, int count) {
        string.append("\t".repeat(count));
    }
}
