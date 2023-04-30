package oop.diskUsage;

class UserInputException extends Exception {

    static final String availableOptions = """
            Available options:

            --limit n\tshow n the heaviest files and / or directories
            --depth n\tset recursion depth n
            -L\t\t\tfollow symlinks""";

    private UserInputException(String message) {
        super(message);
    }

    static UserInputException duplicateOption(String optionName) {
        return new UserInputException(String.format("double definition of '%s' option", optionName));
    }

    static UserInputException noArgument(String optionName) {
        return new UserInputException(String.format("option requires an argument -- '%s'\n\n" + availableOptions, optionName));
    }

    static UserInputException wrongDirectory(String dirName) {
        return new UserInputException(String.format("cannot access '%s': No such file or directory\n\n" + availableOptions, dirName));
    }

    static UserInputException wrongArgument(String optionName) {
        return new UserInputException(String.format("wrong argument for option '%s'\n\n" + availableOptions, optionName));
    }
}
