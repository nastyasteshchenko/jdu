package oop.diskUsage.fileTree;

import oop.diskUsage.file.Directory;
import oop.diskUsage.file.File;
import oop.diskUsage.file.RegularFile;
import oop.diskUsage.file.SymbolicLink;

import java.io.IOException;
import java.nio.file.Files;
public class FileTree {
        public static void fillFileTree(Directory startDir) throws IOException {
            java.io.File[] list = startDir.getPath().toFile().listFiles();
            File f;
            for (java.io.File file : list) {
                if (Files.isDirectory(file.toPath())) {
                    f = new Directory(file.toPath());
                    fillFileTree((Directory) f);
                    startDir.addChild(f);
                    continue;
                }
                if (Files.isRegularFile(file.toPath())) {
                    f = new RegularFile(file.toPath(), Files.size(file.toPath()));
                    startDir.addChild(f);
                    continue;
                }
                if (Files.isSymbolicLink(file.toPath())) {
                    f = new SymbolicLink(file.toPath(), Files.size(file.toPath()));
                    startDir.addChild(f);
                }
            }
        }
}
