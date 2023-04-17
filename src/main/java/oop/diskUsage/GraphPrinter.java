package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;
import oop.diskUsage.file.RegularFileGraphNode;
import oop.diskUsage.file.SymbolicLinkGraphNode;
import oop.diskUsage.file.GraphNode;

import java.util.HashSet;

public class GraphPrinter {

    private static JduOptions jduOptions;

    private static void printTab(int count) {
        for (int j = 0; j < count; j++) {
            System.out.print("\t");
        }
    }

    public static void print(DirectoryGraphNode startDir, JduOptions jduOptions) {
        GraphPrinter.jduOptions = jduOptions;
        print(startDir, 0);
    }

    private static void print(DirectoryGraphNode startDir, int currentDepth) {

        int countFiles = 0;

        printTab(currentDepth);

        System.out.println("/" + startDir.path().getFileName() + " " + SizePrinter.print(startDir.size()));

        if (currentDepth + 1 >= jduOptions.getDepth() - 1) {
            return;
        }

        HashSet<DirectoryGraphNode> visitedSymlinkChildren = new HashSet<>();

        for (GraphNode i : startDir.getChildren()) {

            if (countFiles == jduOptions.getLimitAmountOfFiles()) {
                break;
            }

            countFiles++;

            if (i instanceof DirectoryGraphNode) {

                print((DirectoryGraphNode) i, currentDepth + 1);

                continue;

            }

            if (i instanceof SymbolicLinkGraphNode) {

                printTab(currentDepth + 1);

                System.out.println("*" + i.path().getFileName() + " " + SizePrinter.print(i.size()));

                if (jduOptions.isPassThroughSymLink()) {

                    GraphNode child = ((SymbolicLinkGraphNode) i).getChild();

                    if (child instanceof DirectoryGraphNode) {

                        if (!visitedSymlinkChildren.contains(child)) {

                            visitedSymlinkChildren.add((DirectoryGraphNode) child);

                        } else {

                            continue;
                        }

                        print((DirectoryGraphNode) child, currentDepth + 2);

                    } else {

                        printTab(currentDepth + 2);
                        System.out.println(child.path().getFileName() + " " + SizePrinter.print(child.size()));
                    }

                }

                continue;
            }

            if (i instanceof RegularFileGraphNode) {

                printTab(currentDepth + 1);

                System.out.println(i.path().getFileName() + " " + SizePrinter.print(i.size()));

            }
        }
    }
}
