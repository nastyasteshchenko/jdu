package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.TreeNode;

public class TreeSorter {

    public static void sortTree(DirectoryTreeNode startDir) {

        startDir.getChildren().sort(Comparator.createComparator());

        for (TreeNode i : startDir.getChildren()) {
            if (i instanceof DirectoryTreeNode) {
                sortTree((DirectoryTreeNode) i);
            }
        }

    }

}

