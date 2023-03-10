package oop.diskUsage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StartDirPath {

    private static boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Path createStartDirPath(String[] args) throws IOException {

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
}


record JduOptions {

    //fields from PrintTree

    static JduOptions creste(String[] args) {

    }

}