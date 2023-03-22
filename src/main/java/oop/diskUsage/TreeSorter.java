package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.TreeNode;

import java.util.Comparator;

public class TreeSorter {

    public static void sortTree(DirectoryTreeNode root) {

        // TODO see Comparator.comparing()
        root.getChildren().sort(TreeNodesComparator.createComparator());

        // TODO support cycles
        for (TreeNode i : root.getChildren()) {
            if (i instanceof DirectoryTreeNode) {
                sortTree((DirectoryTreeNode) i);
            }
        }

    }

}

