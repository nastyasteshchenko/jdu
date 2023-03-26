package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.RegularFileTreeNode;
import oop.diskUsage.file.SymbolicLinkTreeNode;
import oop.diskUsage.file.TreeNode;

public class TreePrinter {

    private static void printTab(int count) {
        for (int j = 0; j < count; j++) {
            System.out.print("\t");
        }
    }

    public static void print(DirectoryTreeNode startDir, JduOptions jduOptions)  {
        print(startDir, jduOptions, 0);
    }

    public static void print(DirectoryTreeNode startDir, JduOptions jduOptions, int currentDepth) {

        if (currentDepth >= jduOptions.depth()-1) {
            return;
        }

        int countFiles = 0;

        printTab(currentDepth);

        System.out.println("/" + startDir.path().getFileName() + " " + Measurement.printSizeOfFile(startDir.size()));


        for (TreeNode i : startDir.getChildren()) {

            if (countFiles == jduOptions.limitAmountOfFiles()) {
                break;
            }

            if (i instanceof DirectoryTreeNode) {

                    print((DirectoryTreeNode) i, jduOptions, currentDepth + 1);

                continue;

            }

            if (i instanceof SymbolicLinkTreeNode) {

                printTab(currentDepth + 1);

                System.out.println("*" + i.path().getFileName() + " " + Measurement.printSizeOfFile(i.size()));

                if (jduOptions.passThroughSymLink()) {

                    TreeNode child = ((SymbolicLinkTreeNode) i).getChild();

                    if (child instanceof DirectoryTreeNode) {
                            print((DirectoryTreeNode) child, jduOptions, currentDepth + 2);

                    } else {

                        if (child != null ) {
                            if (currentDepth +1  >= jduOptions.depth()-1) {
                                continue;
                            }
                            printTab(currentDepth+2);
                            System.out.println(child.path().getFileName() + " " + Measurement.printSizeOfFile(child.size()));
                        }

                    }

                }

                continue;
            }

            if (i instanceof RegularFileTreeNode) {

                printTab(currentDepth+1);

                System.out.println(i.path().getFileName() + " " + Measurement.printSizeOfFile(i.size()));

            }

            countFiles++;
        }
    }
}
