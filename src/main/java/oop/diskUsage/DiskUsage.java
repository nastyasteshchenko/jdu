package oop.diskUsage;

import oop.diskUsage.command.Command;
import oop.diskUsage.command.CommandFactory;
import oop.diskUsage.file.Directory;
import oop.diskUsage.fileTree.FileTree;

import java.io.IOException;

public class DiskUsage {

    public static void main(String[] args){

        try {

            Directory startDir = new Directory(StartDirPath.createStartDirPath(args));
            Command cmd = CommandFactory.createCommand(args);
            FileTree.fillFileTree(startDir);
            cmd.apply(startDir);

        } catch (IOException e) { // user shouldn't see that

            System.out.println(e.getMessage());

        }

    }

}
