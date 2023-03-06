package oop.diskUsage.comparator;

import oop.diskUsage.file.File;

public class Comparator {
    public static java.util.Comparator<File> createComparator() {

        return (o1, o2) -> {
            if (o1.getPath() == o2.getPath()) {
                return 0;
            }
            if (o1.size() >= o2.size()) {
                return -1;
            }
            if (o1.size() < o2.size()) {
                return 1;
            }
            return 0;
        };

    }

}

