package oop.diskUsage.file;

import java.nio.file.Path;

public abstract class File{ //tree node

    public abstract String getName();                           //sealed class

    public abstract Path getPath(); //в пути есть метод для имени

    public abstract long size();

    public abstract boolean isDirectory(); // remove, use instanceof instead

}