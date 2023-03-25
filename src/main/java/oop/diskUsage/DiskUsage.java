package oop.diskUsage;

import oop.diskUsage.file.DirectoryTreeNode;

import java.io.IOException;

public class DiskUsage {

    public static void main(String[] args) {

        try {

            JduOptions jduOptions = JduOptionsParser.create(args);

            DirectoryTreeNode root = TreeBuilder.build(jduOptions);

            TreeSorter.sort(root);

            TreePrinter.print(root, jduOptions);

        } catch (UserInputException | IOException e) {

            if (e instanceof UserInputException) {
                System.err.println(e.getMessage());
            }

        }
    }
}
