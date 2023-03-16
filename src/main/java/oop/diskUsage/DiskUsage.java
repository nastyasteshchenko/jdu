package oop.diskUsage;

import java.io.IOException;

public class DiskUsage {

    public static void main(String[] args) {

        try {

            UserInput userInput = new UserInput(args);
            FileTree.fillFileTree(userInput.getStartDir(), userInput.getOptions().passThroughSymLink(), 0);

            if (userInput.getOptions().limitAmountOfFiles() > 1) {
                TreeSorter.sortTree(userInput.getStartDir());
            }

            TreePrinter.printTree(userInput.getStartDir(), userInput.getOptions(), 0);

        } catch (UserInputException | IOException e) {

            if (e instanceof UserInputException) {
                System.err.println(e.getMessage());
            }

        }
    }
}
