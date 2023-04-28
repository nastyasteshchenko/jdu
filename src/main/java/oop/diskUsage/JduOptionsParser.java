package oop.diskUsage;

public class JduOptionsParser {

    static JduOptions parse(String[] args) throws UserInputException {

        JduOptions.Builder builder = new JduOptions.Builder();

        for (int i = 0; i < args.length; ) {
            switch (args[i]) {
                case "--depth" -> {

                    if (!isDigit(args[i + 1])) {
                        throw UserInputException.noArgument("depth");
                    }

                    builder.depth(Integer.parseInt(args[i + 1]));
                    i += 2;
                }

                case "--limit" -> {

                    if (!isDigit(args[i + 1])) {
                        throw UserInputException.noArgument("limit");
                    }

                    builder.limit(Integer.parseInt(args[i + 1]));
                    i += 2;
                }

                case "-L" -> {

                    builder.passThroughSymlink(true);
                    ++i;
                }

                default -> {

                    builder.startDir(args[i]);
                    ++i;
                }
            }
        }

        return builder.build();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isDigit(String str) {

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}