package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.List;

public abstract class File{

    public abstract String getName();

    public abstract Path getPath();

    public abstract List<File> getChildren();

    public abstract long size();

    public abstract boolean isDirectory();

}