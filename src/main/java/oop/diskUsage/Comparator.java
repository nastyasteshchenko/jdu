package oop.diskUsage;

import oop.diskUsage.file.TreeNode;

public class Comparator {
    public static java.util.Comparator<TreeNode> createComparator() {

        return (o1, o2) -> {
            if (o1.path().equals(o2.path())) {
                return 0;
            }
            if (o1.size() > o2.size()) {
                return -1;
            }
            if (o1.size() < o2.size()) {
                return 1;
            }
            return 0;
        };

    }

}

