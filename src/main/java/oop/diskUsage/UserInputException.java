package oop.diskUsage;

public class UserInputException extends Exception {

    public static final String availableOptions = """
            Available options:

            --limit n\tshow n the heaviest files and / or directories
            --depth n\tset recursion depth n
            -L\t\t\tfollow symlinks""";

    private UserInputException(String message) {
        super(message);
    }

    public static UserInputException duplicateOption(String optionName) {
        return new UserInputException(String.format("double definition of '%s' option", optionName));
    }

    public static UserInputException noArgument(String optionName) {
        return new UserInputException(String.format("option requires an argument -- '%s'\n\n" + availableOptions, optionName));
    }

    public static UserInputException wrongDirectory(String dirName) {
        return new UserInputException(String.format("cannot access '%s': No such file or directory\n\n" + availableOptions, dirName));
    }

    public static UserInputException wrongArgument(String optionName) {
        return new UserInputException(String.format("wrong argument for option '%s'\n\n" + availableOptions, optionName));
    }
}
