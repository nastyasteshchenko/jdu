package oop.diskUsage.command;

import oop.diskUsage.file.File;

import java.io.IOException;

public abstract class Command {
    abstract public void apply(File startDir) throws IOException;
}
