package oop.diskUsage;

class JduOptionsParser {

    /**
     * Parses options for JDU entered by user.
     *
     * @param args array of option strings to parse.
     * @return the resulting JduOptions.
     * @throws UserInputException if the user entered an invalid argument, forgot about an argument for an option, duplicated an option or entered a non-existent directory.
     */
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

                    builder.passThroughSymlink();
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