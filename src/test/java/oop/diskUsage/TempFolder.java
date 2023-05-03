package oop.diskUsage;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class TempFolder {

    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    private Path startDirPath;

    public Path getStartDirPath() {
        return startDirPath;
    }

    public TempFolder() {
        createFolder();
    }

    private void createFolder() {
        try {
            folder.create();

            String startDir = folder.getRoot().toString().concat("/dir1");
            startDirPath = Paths.get(startDir);

            folder.newFolder("dir1");
            folder.newFile("dir1/file1");
            folder.newFile("dir1/file2");
            folder.newFolder("dir1/dir2");
            folder.newFolder("dir1/dir2/dir3");
            folder.newFile("dir1/dir2/dir3/Dasha");

            String linkToDir1Path = startDir.concat("/dir2/link_to_dir1");

            Files.createSymbolicLink(Paths.get(linkToDir1Path), startDirPath);

        } catch (IOException e) {
            System.err.println("error creating temporary test file");
        }
    }

}
