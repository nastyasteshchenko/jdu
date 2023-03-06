package oop.diskUsage.file;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Directory extends File {

    private final Path path;

    private final List<File> children = new ArrayList<>();

    public Directory(Path path) {
        this.path = path;
    }

    public void addChild(File file){
        children.add(file);
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public Path getPath() {
        return path;
    }

    //@Override
    public List<File> getChildren() {
        return children;
    }

    @Override
    public long size() {

        long sum = 0;
        for (File file : children) {
            sum += file.size();
        }

        return sum;

    }

    @Override
    public boolean isDirectory() {
        return true;
    }

}
