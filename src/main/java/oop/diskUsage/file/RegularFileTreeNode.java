package oop.diskUsage.file;

import java.nio.file.Path;

public record RegularFileTreeNode(Path path, long size) implements TreeNode {
}
