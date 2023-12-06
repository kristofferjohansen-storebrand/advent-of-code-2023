package no.kristofferjohansen.adventofcode2023.common;

import java.util.Arrays;

public class ParseUtil {

    public static int[] parseToIntArray(final String input, final String delimiter) {
        return Arrays.stream(input.split(delimiter))
                .filter(value -> !value.isBlank())
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static int[] parseToIntArray(final String input) {
        return parseToIntArray(input, " ");
    }
}
