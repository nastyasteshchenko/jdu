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

    private static final Set<Path> visitedSymlinks = new HashSet<>();

    public static DirectoryTreeNode build(JduOptions jduOptions) throws IOException {
        return build(new DirectoryTreeNode(jduOptions.startDir()), jduOptions, 0);
    }

    private static DirectoryTreeNode build(DirectoryTreeNode startDir, JduOptions jduOptions, int currentDepth) throws IOException {

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(startDir.path())) {

            HashSet<Path> symlinksChildren = new HashSet<>();

            for (Path filePath : stream) {

                if (Files.isSymbolicLink(filePath)) {

                    Path pathToChild = Files.readSymbolicLink(filePath);

                    SymbolicLinkTreeNode f = new SymbolicLinkTreeNode(filePath, pathToChild.toString().length());

                    if (jduOptions.passThroughSymLink()) {

                        if (!symlinksChildren.contains(pathToChild)) {

                            if (Files.isDirectory(filePath)) {
                                symlinksChildren.add(pathToChild);
                            }

                        } else {

                            startDir.addChild(f);

                            continue;

                        }

                        if (!visitedSymlinks.contains(filePath)) {

                            if (Files.isDirectory(filePath)) {
                                visitedSymlinks.add(filePath);
                            }

                        } else {

//                        if (Files.isDirectory(filePath)) {
//                            visitedSymlinks.remove(filePath);
//                        }
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

        if (currentDepth == 0) {
            return startDir;
        }

        return null;
    }
}
