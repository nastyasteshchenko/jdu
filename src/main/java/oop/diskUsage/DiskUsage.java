package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DiskUsage {

    private static void handleIOException(IOException e) throws IOException {

        System.err.println("Jdu is failed\nSee the report:");

        Path pathToTmpDir = Files.createTempDirectory(Path.of(System.getProperty("user.dir")), "report");

        Path pathToTmpFile = Files.createTempFile(pathToTmpDir, "error_msg", "");

        System.err.println(pathToTmpDir);

        Files.write(pathToTmpFile, e.getMessage().getBytes());
    }

    public static void main(String[] args) throws IOException {

        JduOptions jduOptions;

        try {

            jduOptions = JduOptionsParser.parse(args);

        } catch (UserInputException e) {

            System.err.println(e.getMessage());

            return;

        }

        DirectoryGraphNode root;

        try {

            root = FileGraphBuilder.build(jduOptions);

        } catch (IOException e) {

            handleIOException(e);

            return;

        }

        GraphSorter.sort(root);

        GraphPrinter.print(root, jduOptions);

    }
}
