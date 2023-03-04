package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.List;

public class RegularFile extends File {

    private final Path path;

    private final long size;

    public RegularFile(Path path, long size){

        this.path = path;
        this.size = size;

    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public List<File> getChildren() {
        return null;
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

}
