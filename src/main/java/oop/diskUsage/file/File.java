package oop.diskUsage.file;

import java.nio.file.Path;

public abstract class File{

    public abstract String getName();

    public abstract Path getPath();

    public abstract long size();

    public abstract boolean isDirectory();

}