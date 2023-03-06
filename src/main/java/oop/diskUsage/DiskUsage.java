package oop.diskUsage;

import oop.diskUsage.command.Command;
import oop.diskUsage.command.CommandFactory;
import oop.diskUsage.file.Directory;
import oop.diskUsage.fileTree.FileTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiskUsage {

    private static Path createStartDirPath(String[]args) throws IOException {

        Path startDirectory;

        if (args.length == 0 || args[args.length - 1].equals("-L") ||
                args.length > 1 && isDigit(args[args.length - 1]) &&
                        (args[args.length - 2].equals("--depth") || args[args.length - 2].equals("--limit"))) {

            startDirectory = Paths.get("").toRealPath();

        } else {

            startDirectory = Paths.get(args[args.length - 1]);
        }

        if (Files.notExists(startDirectory.toAbsolutePath())) {
            System.err.print("No such directory");
            System.exit(1);
        }

        return startDirectory;
    }

    private static boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {

        try {

            Directory startDir = new Directory(createStartDirPath(args));
            Command cmd = CommandFactory.createCommand(args);
            FileTree.fillFileTree(startDir);
            cmd.apply(startDir);

        } catch (IOException e) {

            System.out.println(e.getMessage());

        }

    }

}
