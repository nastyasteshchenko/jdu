package oop.diskUsage.command;

import oop.diskUsage.comparator.CreateComparator;
import oop.diskUsage.measurement.UnitOfMeasurement;
import oop.diskUsage.file.File;

import java.io.IOException;
import java.nio.file.Files;

public class PrintTree extends Command {

    private final int depth;

    private final boolean symLinkOption;

    private final int num;

    private final int limit = 1000;

    public PrintTree(int depth, int num, boolean symLinkOption) {

        this.depth = depth;
        this.num = num;
        this.symLinkOption=symLinkOption;

    }

    private void printTree(File startDir, int currentDepth) throws IOException {

        if (num != -1) {
            startDir.getChildren().sort(CreateComparator.createComparator());
        }

        int countFiles = 0;

        for (int i = 0; i < currentDepth; i++) {
            System.out.print("\t");
        }

        System.out.println("/" + startDir.getName() + " " + UnitOfMeasurement.sizeOfFile(startDir.size()));

        if (currentDepth == depth && depth != -1) {
            return;
        }

        for (File i : startDir.getChildren()) {

            if (countFiles == num) {
                break;
            }

            if (i.isDirectory()) {

                if (Files.isSameFile(i.getPath().getParent(), startDir.getPath().getParent())) {
                    printTree(i, currentDepth);
                } else {
                    printTree(i, currentDepth + 1);
                }

            } else if (Files.isSymbolicLink(i.getPath()) && symLinkOption){

                for (int j = 0; j < currentDepth + 1; j++) {
                    System.out.print("\t");
                }
                System.out.print(i.getName() + " " + UnitOfMeasurement.sizeOfFile(i.size()));

                System.out.println( " -> " + Files.readSymbolicLink(i.getPath()).getFileName()  );
            } else {
                for (int j = 0; j < currentDepth + 1; j++) {
                    System.out.print("\t");
                }

                System.out.println(i.getName() + " " + UnitOfMeasurement.sizeOfFile(i.size()));
            }

            if (num != -1) {
                countFiles++;
            }
        }

    }

    @Override
    public void apply(File startDir) throws IOException {

        if (num > limit) {
            System.err.print("File limit exceeded");
            System.exit(1);
        }

        if (depth > limit) {
            System.err.print("Recurse limit exceeded");
            System.exit(1);
        }

        try {

            printTree(startDir, 0);

        } catch (IOException e){

            System.out.println(e.getMessage());

        }
    }
}
