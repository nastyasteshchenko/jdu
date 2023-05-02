package oop.diskUsage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

record JduOptions(int depth, int limitAmountOfFiles, boolean passThroughSymLink, Path startDir) {
    // TODO move to builder

    static class Builder {
        static final int MAX_DEPTH = 1000;
        static final int MAX_AMOUNT_OF_FILES = 1000;
        static final int MIN_DEPTH = 0;
        static final int MIN_AMOUNT_OF_FILES = 0;
        private static final boolean DEFAULT_PASS_THROUGH_SYMLINK = false;
        private static final Path USER_DIR = Paths.get(System.getProperty("user.dir"));

        private Integer depth;
        private Integer limitAmountOfFiles;
        private Boolean passThroughSymLink;
        private Path startDir;

        @SuppressWarnings("UnusedReturnValue")
        Builder depth(int depth) throws UserInputException {

            checkForDuplicates("depth", this.depth);

            checkRange("depth", depth, MIN_DEPTH, MAX_DEPTH);

            this.depth = depth;

            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder limit(int limit) throws UserInputException {

            checkForDuplicates("limit", this.limitAmountOfFiles);

            checkRange("limit", limit, MIN_AMOUNT_OF_FILES, MAX_AMOUNT_OF_FILES);

            this.limitAmountOfFiles = limit;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder passThroughSymlink() throws UserInputException {

            checkForDuplicates("-L", passThroughSymLink);

            this.passThroughSymLink = true;
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

            fillDefaultForOptions();
            return new JduOptions(depth, limitAmountOfFiles, passThroughSymLink, startDir);
        }

        private void fillDefaultForOptions() {

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

        private <T> void checkForDuplicates(String optionName, T value) throws UserInputException {
            if (value != null) {
                throw UserInputException.duplicateOption(optionName);
            }
        }

        private void checkRange(String option, int value, int from, int to) throws UserInputException {
            if (value < from || value > to) {
                throw UserInputException.wrongArgument(option, from, to);
            }
        }

    }
}