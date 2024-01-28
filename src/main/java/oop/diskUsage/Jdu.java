package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Jdu {

    public static void main(String[] args) {
        run(args, System.out);
    }

    static void run(String[] args, Appendable output) {
        JduOptions jduOptions = parseOptions(args);
        if (jduOptions == null) {
            return;
        }

        DirectoryGraphNode root = buildGraph(jduOptions);
        if (root == null) {
            return;
        }

        FileGraphSorter.sort(root);

        printGraph(root, jduOptions, output);
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

    private static void printGraph(DirectoryGraphNode root, JduOptions jduOptions, Appendable output) {
        try {
            new FileGraphPrinter(jduOptions, output).print(root);
        } catch (IOException e) {
            printErrorReport(e.getMessage());
        }
    }

    private static DirectoryGraphNode buildGraph(JduOptions jduOptions) {
        try {
            return new FileGraphBuilder(jduOptions).build();
        } catch (IOException e) {
            printErrorReport(e.getMessage());
        }
        return null;
    }

    private static void printErrorReport(String errMsg) {

        System.err.println("Jdu is failed");

        try {

            Path pathToTmpDir = Files.createTempDirectory(Path.of(System.getProperty("user.dir")), "report");
            Path pathToTmpFile = Files.createTempFile(pathToTmpDir, "error_msg", "");

            Files.write(pathToTmpFile, errMsg.getBytes());

            System.err.println("See the report:");
            System.err.println(pathToTmpDir);

        } catch (IOException e) {

            System.err.println("Couldn't create a report");
            System.err.println(e.getMessage());

        }
    }
}
