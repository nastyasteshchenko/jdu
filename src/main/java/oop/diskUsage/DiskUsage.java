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

    public static boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static void main(String[] args) throws IOException {

        Path startDirectory;

        if (args.length == 0 || args[args.length - 1].equals("-L") ||
                (args.length >= 2 && isDigit(args[args.length - 1]) &&
                        (args[args.length - 2].equals("--depth") || args[args.length - 2].equals("--limit")))) {

            startDirectory = Paths.get(".").toRealPath();

        } else {

            startDirectory = Paths.get(args[args.length - 1]);
        }

        if (Files.notExists(startDirectory.toAbsolutePath())) {
            System.err.print("No such directory");
            System.exit(1);
        }

        Directory startDir = new Directory(startDirectory);

        Command cmd = CommandFactory.createCommand(args);

        try {

            FileTree.fillFileTree(startDir);
            cmd.apply(startDir);

        } catch (IOException e) {

            System.out.println(e.getMessage());

        }

    }

}
