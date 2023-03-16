package oop.diskUsage.file;

import java.nio.file.Path;

public sealed interface TreeNode permits RegularFileTreeNode, SymbolicLinkTreeNode, DirectoryTreeNode {

    Path path();

    long size();

}