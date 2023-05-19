package oop.diskUsage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

record JduOptions(int depth, int amountOfFiles, boolean passThroughSymLink, Path startDir) {

    static class Builder {
        static final int MAX_DEPTH = 1000;
        static final int MAX_AMOUNT_OF_FILES = 1000;
        static final int MIN_DEPTH = 1;
        static final int MIN_AMOUNT_OF_FILES = 1;
        private static final boolean PASS_THROUGH_SYMLINK = false;
        private static final Path USER_DIR = Paths.get(System.getProperty("user.dir"));

        private Integer depth;
        private Integer amountOfFiles;
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
        Builder amountOfFiles(int amountOfFiles) throws UserInputException {

            checkForDuplicates("limit", this.amountOfFiles);
            checkRange("limit", amountOfFiles, MIN_AMOUNT_OF_FILES, MAX_AMOUNT_OF_FILES);

            this.amountOfFiles = amountOfFiles;
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
            return new JduOptions(depth, amountOfFiles, passThroughSymLink, startDir);
        }

        private void fillDefaultForOptions() {

            if (depth == null) {
                depth = MAX_DEPTH;
            }

            if (amountOfFiles == null) {
                amountOfFiles = MAX_AMOUNT_OF_FILES;
            }

            if (passThroughSymLink == null) {
                passThroughSymLink = PASS_THROUGH_SYMLINK;
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