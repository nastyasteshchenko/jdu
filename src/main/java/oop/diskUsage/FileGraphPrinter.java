package oop.diskUsage;

import oop.diskUsage.file.*;

import java.io.IOException;
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

        if (currFile instanceof GraphCompositeNode) {

            if (currFile instanceof SymbolicLinkGraphNode) {

                output.append("*");
            } else {

                output.append("/");
            }

            output.append(currFile.path().getFileName().toString())
                    .append(" ")
                    .append(FileSizeConverter.convert(currFile.size()))
                    .append("\n");

            List<GraphNode> children = ((GraphCompositeNode) currFile).getChildren();

            int limitAmountOfFiles = 0;
            for (GraphNode file : children) {

                if (file != null) {

                    ++limitAmountOfFiles;
                    if (limitAmountOfFiles > jduOptions.limitAmountOfFiles()) {
                        return;
                    }

                    print(file, depth + 1);

                }
            }

        } else if (currFile instanceof RegularFileGraphNode) {

            output.append(currFile.path().getFileName().toString())
                    .append(" ")
                    .append(FileSizeConverter.convert(currFile.size()))
                    .append("\n");

        }
    }

    private void addTabs(int count) throws IOException {
        output.append("\t".repeat(count));
    }
}