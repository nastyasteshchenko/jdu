package oop.diskUsage;

import java.text.DecimalFormat;

public class Measurement {
    public static String printSizeOfFile(double size){ // TODO double to long

        DecimalFormat df = new DecimalFormat("#.###");

        if (size < Math.pow(10,3)){
            return "[" + size + " B]";
        }
        if (size<Math.pow(10,6)){
            return "[" + df.format(size / Math.pow(10,3)) + " kB]";
        }
        if (size<Math.pow(10,9)){
            return "[" + df.format(size / Math.pow(10,6)) + " MB]";
        }
        if (size<Math.pow(10,12)){
            return "[" + df.format(size / Math.pow(10,9)) + " GB]";
        }
        return " ";
    }

}
