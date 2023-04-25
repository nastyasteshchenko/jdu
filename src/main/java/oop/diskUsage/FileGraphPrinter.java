package oop.diskUsage;

import oop.diskUsage.file.*;

import java.util.List;

class FileGraphPrinter {
    private final JduOptions jduOptions;

    public FileGraphPrinter(JduOptions jduOptions) {
        this.jduOptions = jduOptions;
    }

    public void print(GraphNode root) {
        print(root, 0);
    }

    private void print(GraphNode currFile, int depth) {

        if (depth > jduOptions.depth() - 1) {
            return;
        }

        printTab(depth);

        if (currFile instanceof GraphCompositeNode) {

            if (currFile instanceof SymbolicLinkGraphNode) {

                System.out.print("*");
            } else {

                System.out.print("/");
            }

            System.out.println(currFile.path().getFileName() + " " + FileSizePrinter.print(currFile.size()));

            List<GraphNode> children = ((GraphCompositeNode) currFile).getChildren();

            int limitAmountOfFiles = 0;
            for (GraphNode file : children) {

                ++limitAmountOfFiles;
                if (limitAmountOfFiles > jduOptions.limitAmountOfFiles()) {
                    return;
                }

                print(file, depth + 1);
            }

        } else if (currFile instanceof RegularFileGraphNode) {

            System.out.println(currFile.path().getFileName() + " " + FileSizePrinter.print(currFile.size()));

        }
    }

    private static void printTab(int count) {
        for (int j = 0; j < count; j++) {
            System.out.print("\t");
        }
    }
}
