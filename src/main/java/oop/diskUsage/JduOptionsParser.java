package oop.diskUsage;

public class JduOptionsParser {

    public static JduOptions parse(String[] args) throws UserInputException {

        JduOptions.Builder builder = new JduOptions.Builder();

        for (int i = 0; i < args.length; ) {

            switch (args[i]) {

                case "--depth" -> {

                    builder.depth(args[i + 1]);

                    i += 2;
                }

                case "--limit" -> {

                    builder.limit(args[i + 1]);

                    i += 2;
                }

                case "-L" -> {

                    builder.passThroughtSymlink(true);

                    ++i;
                }

                default -> {

                    if (args.length - 1 == i) {

                        builder.startDir(args[i]);

                    } else {

                        throw UserInputException.invalidOption(args[i]);

                    }

                    ++i;

                }
            }
        }

        return builder.build();
    }
}
