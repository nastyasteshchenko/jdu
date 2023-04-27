package oop.diskUsage;

import java.text.DecimalFormat;

public class FileSizeConverter {
    public static String print(long size){

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
