package oop.diskUsage;

import oop.diskUsage.file.TreeNode;

import java.io.IOException;

public class DiskUsage {

    public static void main(String[] args) {

        try {

            // распарси инпут
            // построй дерево
            // отсортируй
            // выведи

            UserInput userInput = new UserInput(args); // see oop.diskUsage.JduOptionsParser
            FileTree.fillFileTree(userInput.getStartDir(), userInput.getOptions().passThroughSymLink(), 0); // oop.diskUsage.FileTreeBuilder

            TreeSorter.sortTree(userInput.getStartDir());

            TreePrinter.printTree(userInput.getStartDir(), userInput.getOptions(), 0); //remove curentDEpth from public API

        } catch (UserInputException e) {

            System.err.println(e.getMessage());

        } catch (IOException e) {
            // TODO report in temp directory:
            // input args
            // expection stack trace

            // TODO to user:
            // Ooops, jdu is failed. See the report /../../.
        }
    }
}

class JduOptionsParser {
    // TODO add tests
    static JduOptions parse(String[] args) {

    }
}


class FileTreeBuilder {

    static TreeNode build(JduOptions options) {

    }
}