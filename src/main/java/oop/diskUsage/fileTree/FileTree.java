package oop.diskUsage.fileTree;

import oop.diskUsage.file.Directory;
import oop.diskUsage.file.File;
import oop.diskUsage.file.RegularFile;
import oop.diskUsage.file.SymbolicLink;

import java.io.IOException;
import java.nio.file.*;

public class FileTree {
    public static void fillFileTree(Directory startDir) throws IOException {

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(startDir.getPath())) { //прочитать что происходит

            for (Path filePath : stream) {

                File f; //move to if blocks

                if (Files.isDirectory(filePath)) {
                    f = new Directory(filePath);
                    fillFileTree((Directory) f);
                    startDir.addChild(f);
                    continue;
                }

                if (Files.isRegularFile(filePath)) {
                    f = new RegularFile(filePath, Files.size(filePath));
                    startDir.addChild(f);
                    continue;
                }

                if (Files.isSymbolicLink(filePath)) {
                    f = new SymbolicLink(filePath, Files.size(filePath));
                    startDir.addChild(f);
                }

            }

        } catch (IOException | DirectoryIteratorException x) {

            System.err.println(x.getMessage());
            System.exit(1);

        }


    }

}
