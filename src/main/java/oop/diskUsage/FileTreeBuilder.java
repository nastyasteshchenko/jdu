package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;
import oop.diskUsage.file.TreeNode;
import oop.diskUsage.file.RegularFileTreeNode;
import oop.diskUsage.file.SymbolicLinkTreeNode;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class FileTreeBuilder {

    private static final Set<Path> visitedSymlinksChildren = new HashSet<>();

    public static DirectoryTreeNode build(JduOptions jduOptions) throws IOException {
        return build(new DirectoryTreeNode(jduOptions.startDir()), jduOptions, 0);
    }

    private static DirectoryTreeNode build(DirectoryTreeNode startDir, JduOptions jduOptions, int currentDepth) throws IOException {

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(startDir.path())) {

            for (Path filePath : stream) {

                if (Files.isSymbolicLink(filePath)) {

                    Path pathToChild = Files.readSymbolicLink(filePath);

                    SymbolicLinkTreeNode f = new SymbolicLinkTreeNode(filePath, pathToChild.toString().length());

                    if (jduOptions.passThroughSymLink()) {

                        if (!visitedSymlinksChildren.contains(pathToChild)) {

                            if (Files.isDirectory(pathToChild)) {
                                visitedSymlinksChildren.add(pathToChild);
                            }

                        } else {

                            startDir.addChild(f);
                            continue;
                        }
                    }

                    TreeNode fChild;
                    if (Files.isDirectory(pathToChild)) {

                        fChild = new DirectoryTreeNode(pathToChild);

                        if (jduOptions.passThroughSymLink() && currentDepth < jduOptions.depth()) {

                            build((DirectoryTreeNode) fChild, jduOptions, currentDepth + 1);

                        }

                    } else {

                        fChild = new RegularFileTreeNode(pathToChild, Files.size(pathToChild));
                    }

                    f.addChild(fChild);

                    startDir.addChild(f);
                    continue;
                }

                if (Files.isDirectory(filePath)) {

                    DirectoryTreeNode f = new DirectoryTreeNode(filePath);
                    build(f, jduOptions, currentDepth + 1);
                    startDir.addChild(f);
                    continue;

                }

                if (Files.isRegularFile(filePath)) {

                    TreeNode f = new RegularFileTreeNode(filePath, Files.size(filePath));
                    startDir.addChild(f);

                }
            }
        }

        return startDir;
    }
}
