package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO expected start dir: either input from args or current dir from system props
record JduOptions(int depth, int limitAmountOfFiles, boolean passThroughSymLink) {
    static final int DEFAULT_DEPTH = 1000;
    static final int DEFAULT_LIMIT_AMOUNT_OF_FILES = 1000;
    static final boolean DEFAULT_PASS_THROUGH_SYMLINK = false;
}

public class UserInput {

    private static final String availableOptions = """
            Available options:

            --limit n\tshow n the heaviest files and / or directories
            --depth n\tset recursion depth n
            -L\t\t\tfollow symlinks""";

    private final JduOptions jduOptions;
    private DirectoryTreeNode startDir = new DirectoryTreeNode(Paths.get("").toRealPath());

    UserInput(String[] args) throws IOException, UserInputException {
        jduOptions = create(args);
    }

    public JduOptions getOptions() {
        return jduOptions;
    }

    public DirectoryTreeNode getStartDir() {
        return startDir;
    }

    private boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private JduOptions create(String[] args) throws UserInputException {

        int depth = JduOptions.DEFAULT_DEPTH;
        int limitAmountOfFiles = JduOptions.DEFAULT_LIMIT_AMOUNT_OF_FILES;
        boolean passThroughSymLink = JduOptions.DEFAULT_PASS_THROUGH_SYMLINK;

        // TODO what if input params was set twice? TODO add tests
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
                    if (!isDigit(args[i + 1])) { // TODO remove double check
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
                        startDir = new DirectoryTreeNode(dir);
                    } else {
                        throw new UserInputException("invalid option '" + args[i] + "'\n\n" + availableOptions);
                    }
                    ++i;

                }
            }
        }
        return new JduOptions(depth, limitAmountOfFiles, passThroughSymLink);

    }
}
