package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.RegularFileTreeNode;
import oop.diskUsage.file.SymbolicLinkTreeNode;
import oop.diskUsage.file.TreeNode;

import java.io.IOException;
import java.nio.file.Files;

public class TreePrinter {

    private static void printTab(int count) {
        for (int j = 0; j < count; j++) {
            System.out.print("\t");
        }
    }

    // TODO remove currentDepth from public API
    // TODO support cycles
    public static void printTree(DirectoryTreeNode startDir, JduOptions jduOptions, int currentDepth) throws IOException {

        int countFiles = 0;

        printTab(currentDepth);

        System.out.println("/" + startDir.path().getFileName() + " " + Measurement.printSizeOfFile(startDir.size()));

        if (currentDepth == jduOptions.depth()) {
            return;
        }

        for (TreeNode i : startDir.getChildren()) {

            if (countFiles == jduOptions.limitAmountOfFiles()) {
                break;
            }

            if (i instanceof DirectoryTreeNode) {

                if (Files.isSameFile(i.path().getParent(), startDir.path().getParent())) {
                    printTree((DirectoryTreeNode) i, jduOptions, currentDepth);
                } else {
                    printTree((DirectoryTreeNode) i, jduOptions, currentDepth + 1);
                }

                continue;

            }

            if (i instanceof SymbolicLinkTreeNode) {

                printTab(currentDepth + 1);

                System.out.print(i.path().getFileName() + " " + Measurement.printSizeOfFile(i.size()));

                if (jduOptions.passThroughSymLink()) {

                    System.out.println(" -> ");

                    printTab(currentDepth + 2);

                    if (((SymbolicLinkTreeNode) i).getChild() == null) {
                        System.out.println(Files.readSymbolicLink(i.path()));
                        continue;
                    }

                    TreeNode child = ((SymbolicLinkTreeNode) i).getChild();

                    if (child instanceof DirectoryTreeNode) {

                        if (Files.isSameFile(i.path().getParent(), startDir.path().getParent())) {
                            printTree((DirectoryTreeNode) child, jduOptions, currentDepth);
                        } else {
                            printTree((DirectoryTreeNode) child, jduOptions, currentDepth + 1);
                        }

                    } else {

                        System.out.print(child.path().getFileName() + " " + Measurement.printSizeOfFile(child.size()));
                    }

                }
                System.out.println();

                continue;
            }

            if (i instanceof RegularFileTreeNode) {

                for (int j = 0; j < currentDepth + 1; j++) {
                    System.out.print("\t");
                }

                System.out.println(i.path().getFileName() + " " + Measurement.printSizeOfFile(i.size()));

            }

            countFiles++;
        }
    }
}
