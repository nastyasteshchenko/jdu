package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.TreeNode;

public class TreeSorter {

    public static void sort(DirectoryTreeNode startDir) {

        startDir.getChildren().sort(TreeNodesComparator.createComparator());

        for (TreeNode i : startDir.getChildren()) {
            if (i instanceof DirectoryTreeNode) {
                sort((DirectoryTreeNode) i);
            }
        }

    }

}

