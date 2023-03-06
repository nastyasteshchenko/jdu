package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.List;

public abstract class File{
    //CCR: getName, getPath got exactly same implementations everywhere. Implement here

    public abstract String getName();

    public abstract Path getPath();

    //CCR: move getChildren to Directory and check instance (specific for Directory)
    public abstract List<File> getChildren();

    public abstract long size();

    public abstract boolean isDirectory();
    // CCR: public boolean isDirectory(){return true}; Override in Directory
}