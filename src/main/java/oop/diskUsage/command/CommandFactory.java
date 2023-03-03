package oop.diskUsage.command;

public class CommandFactory {
    public static Command createCommand(String[] sCmd) {
        Command cmd = null;
        if (sCmd.length <= 3) {
            switch (sCmd[0]) {
                case "--depth" -> cmd = new PrintTree(Integer.parseInt(sCmd[1]), -1, -1);
                case "--limit" -> cmd = new PrintTree(-1, Integer.parseInt(sCmd[1]), -1);
                case "-L" -> cmd = new PrintTree(-1, -1, 1);
                default -> cmd = new PrintTree(-1, -1, -1);
            }
        } else if (sCmd.length <= 5) {
            if (sCmd[0].equals("--depth") && sCmd[2].equals("--limit")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[1]), Integer.parseInt(sCmd[3]), -1);
            }
            if (sCmd[2].equals("--depth") && sCmd[0].equals("--limit")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[3]), Integer.parseInt(sCmd[1]), -1);
            }
            if (sCmd[0].equals("-L") && sCmd[1].equals("--limit")) {
                cmd = new PrintTree(-1, Integer.parseInt(sCmd[2]), 1);
            }
            if (sCmd[0].equals("--limit") && sCmd[2].equals("-L")) {
                cmd = new PrintTree(-1, Integer.parseInt(sCmd[1]), 1);
            }
            if (sCmd[0].equals("--depth") && sCmd[2].equals("-L")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[1]), -1, 1);
            }
            if (sCmd[0].equals("-L") && sCmd[1].equals("--depth")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[2]), -1, 1);
            }
        } else if (sCmd.length <= 7) {
            if (sCmd[0].equals("--depth") && sCmd[2].equals("--limit") && sCmd[4].equals("-L")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[1]), Integer.parseInt(sCmd[3]), 1);
            }
            if (sCmd[0].equals("--depth") && sCmd[2].equals("-L") && sCmd[3].equals("--limit")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[1]), Integer.parseInt(sCmd[4]), 1);
            }
            if (sCmd[0].equals("-L") && sCmd[1].equals("--limit") && sCmd[3].equals("--depth")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[4]), Integer.parseInt(sCmd[2]), 1);
            }
            if (sCmd[0].equals("-L") && sCmd[1].equals("--depth") && sCmd[3].equals("--limit")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[2]), Integer.parseInt(sCmd[4]), 1);
            }
            if (sCmd[0].equals("--depth") && sCmd[2].equals("-L") && sCmd[3].equals("--limit")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[1]), Integer.parseInt(sCmd[4]), 1);
            }
            if (sCmd[0].equals("--limit") && sCmd[2].equals("-L") && sCmd[3].equals("--depth")) {
                cmd = new PrintTree(Integer.parseInt(sCmd[4]), Integer.parseInt(sCmd[1]), 1);
            }
        }
        return cmd;
}
}
