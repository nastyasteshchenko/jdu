package oop.diskUsage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

record JduOptions(int depth, int limitAmountOfFiles, boolean passThroughSymLink, Path startDir) {
    static final int DEFAULT_DEPTH = 1000;
    static final int DEFAULT_LIMIT_AMOUNT_OF_FILES = 1000;
    static final boolean DEFAULT_PASS_THROUGH_SYMLINK = false;
    static final Path DEFAULT_PATH_STARTDIR =  Paths.get(System.getProperty("user.dir")) ;
}

public class JduOptionsParser {

    private static final String availableOptions = """
            Available options:

            --limit n\tshow n the heaviest files and / or directories
            --depth n\tset recursion depth n
            -L\t\t\tfollow symlinks""";

    private static boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static JduOptions create(String[] args) throws UserInputException {

        int depth = JduOptions.DEFAULT_DEPTH;
        int limitAmountOfFiles = JduOptions.DEFAULT_LIMIT_AMOUNT_OF_FILES;
        boolean passThroughSymLink = JduOptions.DEFAULT_PASS_THROUGH_SYMLINK;
        Path startDir = JduOptions.DEFAULT_PATH_STARTDIR;

        for (int i = 0; i < args.length; ) {
            switch (args[i]) {
                case "--depth" -> {
                    if (!isDigit(args[i + 1])) {
                        throw new UserInputException("option requires an argument -- 'depth'");
                    }

                    depth = Integer.parseInt(args[i + 1]);
                    i += 2;
                }
                case "--limit" -> {
                    if (!isDigit(args[i + 1])) {
                        if (!isDigit(args[i + 1])) {
                            throw new UserInputException("option requires an argument -- 'limit'");
                        }
                    }
                    limitAmountOfFiles = Integer.parseInt(args[i + 1]);
                    i += 2;
                }
                case "-L" -> {
                    passThroughSymLink = true;
                    ++i;
                }
                default -> {

                    if (args.length - 1 == i) {
                        Path dir = Paths.get(args[i]);
                        if (Files.notExists(dir.toAbsolutePath())) {
                            throw new UserInputException("cannot access '" + args[i] + "': No such file or directory");
                        }
                        startDir = dir;
                    } else {
                        throw new UserInputException("invalid option '" + args[i] + "'\n\n" + availableOptions);
                    }
                    ++i;

                }
            }
        }
        return new JduOptions(depth, limitAmountOfFiles, passThroughSymLink, startDir);

    }
}
