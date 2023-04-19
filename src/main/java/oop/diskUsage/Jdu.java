package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Jdu {

    public static void main(String[] args) throws IOException {
        JduOptions jduOptions = parseOptions(args);
        if (jduOptions == null) {
            return;
        }

        DirectoryGraphNode root = buildGraph(jduOptions);
        if (root == null) {
            return;
        }

        FileGraphSorter.sort(root);

        FileGraphPrinter.print(root, jduOptions);
    }

    private static JduOptions parseOptions(String[] args) {
        JduOptions jduOptions;

        try {

            jduOptions = JduOptionsParser.parse(args);

        } catch (UserInputException e) {

            System.err.println(e.getMessage());

            return null;

        }
        return jduOptions;
    }

    private static DirectoryGraphNode buildGraph(JduOptions jduOptions) throws IOException {
        try {
            return new FileGraphBuilder(jduOptions).build();
        } catch (IOException e) {
            printErrorReport(e.getMessage());
        }
        return null;
    }

    private static void printErrorReport(String errMsg) throws IOException {

        System.err.println("Jdu is failed\nSee the report:");

        Path pathToTmpDir = Files.createTempDirectory(Path.of(System.getProperty("user.dir")), "report");

        Path pathToTmpFile = Files.createTempFile(pathToTmpDir, "error_msg", "");

        System.err.println(pathToTmpDir);

        Files.write(pathToTmpFile, errMsg.getBytes());
    }
}
