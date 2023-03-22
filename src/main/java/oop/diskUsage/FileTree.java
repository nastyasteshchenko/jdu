package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.TreeNode;
import oop.diskUsage.file.RegularFileTreeNode;
import oop.diskUsage.file.SymbolicLinkTreeNode;

import java.io.IOException;
import java.nio.file.*;

public class FileTree {

    private static String startDirectoryPath;
    static final int LIMIT_DEPTH = 1000;

    public static void fillFileTree(DirectoryTreeNode startDir, boolean passThroughSymLink) {
        fillFileTree(startDir, passThroughSymLink, 0);
    }

    private static void fillFileTree(DirectoryTreeNode startDir, boolean passThroughSymLink, int currentDepth) throws IOException {


        if (currentDepth == 0) {
            startDirectoryPath = startDir.path().toString();
        }

        if (currentDepth == LIMIT_DEPTH) {
            return;
        }

        // TODO fix
        DirectoryStream<Path> stream = Files.newDirectoryStream(startDir.path());
        for (Path filePath : stream) {

            if (Files.isSymbolicLink(filePath)) {

                Path pathToChild = Files.readSymbolicLink(filePath);

                SymbolicLinkTreeNode f = new SymbolicLinkTreeNode(filePath, pathToChild.toString().length());

                // TODO think about symlink dest, when it's a not a part of our hierarchy
                if (passThroughSymLink) {

                    Path child = filePath.getParent().resolve(pathToChild).toRealPath();

                    if (!child.toString().startsWith(startDirectoryPath) || Files.isRegularFile(child) || Files.isSymbolicLink(child)) {

                        TreeNode fChild;
                        if (Files.isDirectory(child)) {

                            fChild = new DirectoryTreeNode(child);
                            fillFileTree((DirectoryTreeNode) fChild, true, currentDepth + 1);

                        } else {

                            fChild = new RegularFileTreeNode(child, Files.size(child));
                        }

                        f.addChild(fChild);
                    }
                }

                startDir.addChild(f);
                continue;
            }

            if (Files.isDirectory(filePath)) {

                DirectoryTreeNode f = new DirectoryTreeNode(filePath);
                fillFileTree(f, passThroughSymLink, currentDepth + 1);
                startDir.addChild(f);
                continue;

            }

            if (Files.isRegularFile(filePath)) {

                TreeNode f = new RegularFileTreeNode(filePath, Files.size(filePath));
                startDir.addChild(f);

            }
        }
    }
}
