package oop.diskUsage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JduOptions {
    private int depth;
    private int limitAmountOfFiles;
    private boolean passThroughSymLink;
    private Path startDir;

    // TODO check modifiers
    static final int MIN_DEPTH = 0;
    static final int MIN_AMOUNT_OF_FILES = 0;
    static final int MAX_DEPTH = 1000;
    static final int MAX_AMOUNT_OF_FILES = 1000;
    static final boolean DEFAULT_PASS_THROUGH_SYMLINK = false;
    static final Path USER_DIR = Paths.get(System.getProperty("user.dir"));

    public JduOptions(int depth, int limitAmountOfFiles, boolean passThroughSymLink, Path startDir) {
        this.depth = depth;
        this.limitAmountOfFiles = limitAmountOfFiles;
        this.passThroughSymLink = passThroughSymLink;
        this.startDir = startDir;
    }

    public int getDepth() {
        return depth;
    }

    public Path getStartDir() {
        return startDir;
    }

    public int getLimitAmountOfFiles() {
        return limitAmountOfFiles;
    }

    public boolean passThroughSymLink() {
        return passThroughSymLink;
    }

    // TODO rewrite builder according to new field layout and API
    static class Builder {

        private Integer depth;
        private Integer limitAmountOfFiles;
        private Boolean passThroughSymLink;
        private Path startDir;

        @SuppressWarnings("ReturnValueOfTheMethodIsNeverUsed")
        public Builder depth(int depth) throws UserInputException {
            if (jduOptions.depth != null) {
                throw UserInputException.duplicateOption("depth");
            }

            if (!isDigit(depth)) {
                throw UserInputException.noArgument("depth");
            }

            int isInSegment = inSegment(Integer.parseInt(depth), MIN_DEPTH, MAX_DEPTH);

            switch (isInSegment) {
                case -1 -> throw UserInputException.wrongArgument("depth");
                case 1 -> throw UserInputException.limitExceeded("depth");
            }

            jduOptions.depth = Integer.parseInt(depth);

            return this;
        }

        @SuppressWarnings("ReturnValueOfTheMethodIsNeverUsed")
        public Builder limit(int limit) throws UserInputException {
            if (jduOptions.limitAmountOfFiles != null) {
                throw UserInputException.duplicateOption("limit");
            }

            if (!isDigit(limit)) {
                throw UserInputException.noArgument("limit");
            }

            int isInSegment = inSegment(Integer.parseInt(limit), MIN_AMOUNT_OF_FILES, MAX_AMOUNT_OF_FILES);

            switch (isInSegment) {
                case -1 -> throw UserInputException.wrongArgument("limit");
                case 1 -> throw UserInputException.limitExceeded("limit");
            }

            jduOptions.limitAmountOfFiles = Integer.parseInt(limit);
            return this;
        }

        @SuppressWarnings("ReturnValueOfTheMethodIsNeverUsed")
        public Builder passThroughSymlink(boolean passThroughSymLink) throws UserInputException {

            if (jduOptions.passThroughSymLink != null) {
                throw UserInputException.duplicateOption("-L");
            }

            jduOptions.passThroughSymLink = passThroughSymLink;
            return this;
        }

        @SuppressWarnings("ReturnValueOfTheMethodIsNeverUsed")
        public Builder startDir(String startDir) throws UserInputException {

            Path dir = Paths.get(startDir);

            if (Files.notExists(dir.toAbsolutePath())) {
                throw UserInputException.wrongDirectory(startDir);
            }

            jduOptions.startDir = dir;
            return this;
        }

        JduOptions build() {
            if (jduOptions.depth == null) {
                jduOptions.depth = MAX_DEPTH;
            }
            if (jduOptions.limitAmountOfFiles == null) {
                jduOptions.limitAmountOfFiles = MAX_AMOUNT_OF_FILES;
            }
            if (jduOptions.passThroughSymLink == null) {
                jduOptions.passThroughSymLink = DEFAULT_PASS_THROUGH_SYMLINK;
            }
            if (jduOptions.startDir == null) {
                jduOptions.startDir = USER_DIR;
            }
            return new JduOptions(depth, limitAmountOfFiles, passThroughSymLink, startDir);
        }

        // TODO just check, that value is in [a, b]. if not, throw a single type exception
        private int inSegment(int value, int a, int b) {

            if (value < a) {
                return -1;
            }

            if (value > b) {
                return 1;
            }

            return 0;
        }

    }
}
