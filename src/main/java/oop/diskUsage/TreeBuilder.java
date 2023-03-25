package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.TreeNode;
import oop.diskUsage.file.RegularFileTreeNode;
import oop.diskUsage.file.SymbolicLinkTreeNode;

import java.io.IOException;
import java.nio.file.*;

public class TreeBuilder {

    private static String startDirectoryPath;
    static final int LIMIT_DEPTH = 1000;

    public static DirectoryTreeNode build(JduOptions jduOptions) throws IOException {
        return build(new DirectoryTreeNode(jduOptions.startDir()), jduOptions.passThroughSymLink(), 0);
    }

    public static DirectoryTreeNode build(DirectoryTreeNode startDir, boolean passThroughSymLink, int currentDepth) throws IOException {

        if (currentDepth == 0) {
            startDirectoryPath = startDir.path().toString();
        }

        if (currentDepth == LIMIT_DEPTH) {
            return null;
        }

        DirectoryStream<Path> stream = Files.newDirectoryStream(startDir.path());

        for (Path filePath : stream) {

            if (Files.isSymbolicLink(filePath)) {

                Path pathToChild = Files.readSymbolicLink(filePath);

                SymbolicLinkTreeNode f = new SymbolicLinkTreeNode(filePath, pathToChild.toString().length());

                if (passThroughSymLink) {

                    Path child = filePath.getParent().resolve(pathToChild).toRealPath();

                    if (!child.toString().startsWith(startDirectoryPath) || Files.isRegularFile(child) || Files.isSymbolicLink(child)) {

                        TreeNode fChild;
                        if (Files.isDirectory(child)) {

                            fChild = new DirectoryTreeNode(child);
                            build((DirectoryTreeNode) fChild, true, currentDepth + 1);

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
                build(f, passThroughSymLink, currentDepth + 1);
                startDir.addChild(f);
                continue;

            }

            if (Files.isRegularFile(filePath)) {

                TreeNode f = new RegularFileTreeNode(filePath, Files.size(filePath));
                startDir.addChild(f);

            }
        }
        if (currentDepth == 0) {
            return startDir;
        }
        return null;
    }
}
