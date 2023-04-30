package oop.diskUsage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

record JduOptions(int depth, int limitAmountOfFiles, boolean passThroughSymLink, Path startDir) {
    static final int MAX_DEPTH = 1000;
    static final int MAX_AMOUNT_OF_FILES = 1000;
    private static final int MIN_DEPTH = 0;
    private static final int MIN_AMOUNT_OF_FILES = 0;
    private static final boolean DEFAULT_PASS_THROUGH_SYMLINK = false;
    private static final Path USER_DIR = Paths.get(System.getProperty("user.dir"));

    static class Builder {
        private Integer depth;
        private Integer limitAmountOfFiles;
        private Boolean passThroughSymLink;
        private Path startDir;

        @SuppressWarnings("UnusedReturnValue")
        Builder depth(int depth) throws UserInputException {

            if (this.depth != null) {
                throw UserInputException.duplicateOption("depth");
            }

            if (!isInSegment(depth, MIN_DEPTH, MAX_DEPTH)) {
                throw UserInputException.wrongArgument("depth");
            }

            this.depth = depth;

            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder limit(int limit) throws UserInputException {

            if (this.limitAmountOfFiles != null) {
                throw UserInputException.duplicateOption("limit");
            }

            if (!isInSegment(limit, MIN_AMOUNT_OF_FILES, MAX_AMOUNT_OF_FILES)) {
                throw UserInputException.wrongArgument("limit");
            }

            this.limitAmountOfFiles = limit;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder passThroughSymlink(@SuppressWarnings("SameParameterValue") boolean passThroughSymLink) throws UserInputException {

            if (this.passThroughSymLink != null) {
                throw UserInputException.duplicateOption("-L");
            }

            this.passThroughSymLink = passThroughSymLink;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder startDir(String startDir) throws UserInputException {

            Path dir = Paths.get(startDir);

            if (Files.notExists(dir.toAbsolutePath())) {
                throw UserInputException.wrongDirectory(startDir);
            }

            this.startDir = dir;
            return this;
        }

        JduOptions build() {

            fillNullOptions();
            return new JduOptions(depth, limitAmountOfFiles, passThroughSymLink, startDir);
        }

        private void fillNullOptions() {

            if (depth == null) {
                depth = MAX_DEPTH;
            }

            if (limitAmountOfFiles == null) {
                limitAmountOfFiles = MAX_AMOUNT_OF_FILES;
            }

            if (passThroughSymLink == null) {
                passThroughSymLink = DEFAULT_PASS_THROUGH_SYMLINK;
            }

            if (startDir == null) {
                startDir = USER_DIR;
            }
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        private boolean isInSegment(int value, int a, int b) {
            return value >= a && value <= b;
        }

    }
}