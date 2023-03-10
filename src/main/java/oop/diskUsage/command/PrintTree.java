package oop.diskUsage.command;

import oop.diskUsage.comparator.Comparator;
import oop.diskUsage.file.Directory;
import oop.diskUsage.measurement.UnitOfMeasurement;
import oop.diskUsage.file.File;

import java.io.IOException;
import java.nio.file.Files;

public class PrintTree extends Command { //tree printer

    static final int LIMIT = 1000; // for depth
    private final int depth;

    private final boolean symLinkOption; //pass through symlink

    private final int num; //amount of file

    public PrintTree(int depth, int num, boolean symLinkOption) {

        this.depth = depth;
        this.num = num;
        this.symLinkOption = symLinkOption;

    }

    private void printTree(Directory startDir, int currentDepth) throws IOException {

        startDir.getChildren().sort(Comparator.createComparator()); //class tree sorter

        int countFiles = 0;

        for (int i = 0; i < currentDepth; i++) {
            System.out.print("\t");
        }

        System.out.println("/" + startDir.getName() + " " + UnitOfMeasurement.sizeOfFile(startDir.size()));

        if (currentDepth == depth) {
            return;
        }

        for (File i : startDir.getChildren()) {

            if (countFiles == num) {
                break;
            }

            if (i.isDirectory()) {

                if (Files.isSameFile(i.getPath().getParent(), startDir.getPath().getParent())) {
                    printTree((Directory) i, currentDepth);
                } else {
                    printTree((Directory) i, currentDepth + 1);
                }

            } else if (Files.isSymbolicLink(i.getPath()) && symLinkOption) {

                for (int j = 0; j < currentDepth + 1; j++) {
                    System.out.print("\t");
                }
                System.out.print(i.getName() + " " + UnitOfMeasurement.sizeOfFile(i.size()));

                System.out.println(" -> " + Files.readSymbolicLink(i.getPath()).getFileName());

            } else {

                for (int j = 0; j < currentDepth + 1; j++) {
                    System.out.print("\t");
                }

                System.out.println(i.getName() + " " + UnitOfMeasurement.sizeOfFile(i.size()));

            }

            countFiles++;
        }

    }

    @Override
    public void apply(Directory startDir) {

        if (num > LIMIT) {
            System.err.print("File limit exceeded");
            System.exit(1);
        }

        if (depth > LIMIT) {
            System.err.print("Recurse limit exceeded");
            System.exit(1);
        }

        try {

            printTree(startDir, 0);

        } catch (IOException e) {

            System.out.println(e.getMessage());

        }
    }
}
