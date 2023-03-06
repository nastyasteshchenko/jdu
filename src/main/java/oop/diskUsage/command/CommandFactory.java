package oop.diskUsage.command;

public class CommandFactory {

    public static final int DEFAULT_DEPTH = 999;
    public static final int DEFAULT_LIMIT = 999;
    public static boolean DEFAULT_SYMLINK_OPTION = false;

    public static boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static Command createCommand(String[] sCmd) {

        int depth = DEFAULT_DEPTH;
        int limit = DEFAULT_LIMIT;
        boolean symLinkOption = DEFAULT_SYMLINK_OPTION;

        for (int i = 0; i < sCmd.length - 1; ) {

            switch (sCmd[i]) {
                case "--depth" -> {
                    if (isDigit(sCmd[i + 1])) {
                        System.err.print("No arguments for option '--depth'");
                        System.exit(1);
                    }

                    depth = Integer.parseInt(sCmd[i + 1]);
                    i += 2;
                }
                case "--limit" -> {
                    if (isDigit(sCmd[i + 1])) {
                        System.err.print("No arguments for option '--limit'");
                        System.exit(1);
                    }
                    limit = Integer.parseInt(sCmd[i + 1]);
                    i += 2;
                }
                case "-L" -> {
                    symLinkOption = true;
                    i++;
                }
                default -> {
                    System.err.print("No such command '" + sCmd[i] +"'");
                    System.exit(1);
                }
            }

        }

        return new PrintTree(depth, limit, symLinkOption);
    }
}
