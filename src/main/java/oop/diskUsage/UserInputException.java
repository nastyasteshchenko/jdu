package oop.diskUsage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class UserInputException extends Exception{

    private static final String availableOptions = """
            Available options:

            --limit n\tshow n the heaviest files and / or directories
            --depth n\tset recursion depth n
            -L\t\t\tfollow symlinks""";
    public UserInputException(String message){
        super(message);
    }

    static UserInputException duplicateOption(String optionName) {
        return new UserInputException(String.format("double definition of -- '%s' option", optionName));
    }

    static UserInputException noArgument(String optionName) {
        return new UserInputException(String.format("option requires an argument -- '%s'", optionName));
    }

    static UserInputException wrongDirectory(String dirName) {
        return new UserInputException(String.format("cannot access '%s': No such file or directory", dirName));
    }

    static UserInputException limitExceeded(String optionName) {
        return new UserInputException(String.format("limit exceeded for option -- '%s'", optionName));
    }

    static UserInputException wrongArgument(String optionName) {
        return new UserInputException(String.format("wrong argument for option -- '%s'", optionName));
    }

    static UserInputException invalidOption(String optionName) {
        return new UserInputException(String.format("no such option '%s'\n\n" + availableOptions, optionName));
    }

}
