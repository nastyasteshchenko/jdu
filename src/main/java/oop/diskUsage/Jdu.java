package oop.diskUsage;

import oop.diskUsage.file.DirectoryGraphNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// TODO seems the public modifier is redundant almost everywhere, please check all modifiers, make them as private as possible
public class Jdu {

    public static void main(String[] args) throws IOException {
        run(args, System.out);
    }

    static void run(String[] args, Appendable output) throws IOException {
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
        new FileGraphPrinter(jduOptions).print(root);
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