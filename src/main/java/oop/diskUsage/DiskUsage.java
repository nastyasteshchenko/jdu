package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiskUsage {

    public static void main(String[] args) throws IOException {

        try {

            JduOptions jduOptions = JduOptionsParser.create(args);

            DirectoryTreeNode root = FileTreeBuilder.build(jduOptions);

            TreeSorter.sort(root);

            TreePrinter.print(root, jduOptions);

        } catch (UserInputException e) {

            System.err.println(e.getMessage());

        } catch (IOException e){

            System.err.println("Jdu is failed\nSee the report:");

            Path pathToTmpDir = Files.createTempDirectory(Path.of(System.getProperty("user.dir")), "report");

            Path pathToTmpFile = Files.createTempFile(pathToTmpDir, "error_msg", "");

            System.err.println(pathToTmpDir);

            Files.write(pathToTmpFile, e.getMessage().getBytes());

        }
    }
}
