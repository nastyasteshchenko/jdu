package oop.diskUsage.measurement;

import java.text.DecimalFormat;

public class UnitOfMeasurement {
    public static String sizeOfFile(double size){

        DecimalFormat df = new DecimalFormat("#.###");

        if (size < Math.pow(2,10)){
            return "[" + size + " B]";
        }
        if (size<Math.pow(2,20)){
            return "[" + df.format(size / Math.pow(2,10)) + " KiB]";
        }
        if (size<Math.pow(2,30)){
            return "[" + df.format(size / Math.pow(2,20)) + " MiB]";
        }
        if (size<Math.pow(2,40)){
            return "[" + df.format(size / Math.pow(2,30)) + " GiB]";
        }

        return " ";

    }

}
