package oop.diskUsage.command;

import oop.diskUsage.file.Directory;
import oop.diskUsage.file.File;
import java.io.IOException;

public abstract class Command {

    abstract public void apply(Directory startDir) throws IOException;
}
