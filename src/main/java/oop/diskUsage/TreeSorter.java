package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.SymbolicLinkTreeNode;
import oop.diskUsage.file.TreeNode;

import java.util.Comparator;

public class TreeSorter {

    public static void sort(DirectoryTreeNode startDir) {

        startDir.getChildren().sort(Comparator.comparing(TreeNode::size).reversed());

        for (TreeNode i : startDir.getChildren()) {
            if (i instanceof DirectoryTreeNode) {
                sort((DirectoryTreeNode) i);
            }
            if (i instanceof SymbolicLinkTreeNode && ((SymbolicLinkTreeNode) i).getChild() instanceof  DirectoryTreeNode) {
                sort((DirectoryTreeNode) ((SymbolicLinkTreeNode) i).getChild());
            }
        }

    }

}

