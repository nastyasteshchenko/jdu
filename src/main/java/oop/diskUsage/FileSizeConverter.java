package oop.diskUsage;

import java.text.DecimalFormat;

public class FileSizeConverter {

    private static final double THIRD_POWER_OF_TEN = Math.pow(10, 3);
    private static final double SIXTH_POWER_OF_TEN = Math.pow(10, 6);
    private static final double NINTH_POWER_OF_TEN = Math.pow(10, 6);
    private static final double TWELFTH_POWER_OF_TEN = Math.pow(10, 12);

    public static String print(long size) {

        DecimalFormat df = new DecimalFormat("#.###");

        if (size < THIRD_POWER_OF_TEN) {
            return "[" + size + " B]";
        }
        if (size < SIXTH_POWER_OF_TEN) {
            return "[" + df.format(size / THIRD_POWER_OF_TEN) + " kB]";
        }
        if (size < NINTH_POWER_OF_TEN) {
            return "[" + df.format(size / SIXTH_POWER_OF_TEN) + " MB]";
        }
        if (size < TWELFTH_POWER_OF_TEN) {
            return "[" + df.format(size / NINTH_POWER_OF_TEN) + " GB]";
        }
        return " ";
    }

}
