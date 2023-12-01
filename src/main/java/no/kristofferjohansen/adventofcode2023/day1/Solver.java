package no.kristofferjohansen.adventofcode2023.day1;

import no.kristofferjohansen.adventofcode2023.common.FileUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Solver {

    private static final Map<Integer, Pattern> DIGIT_PATTERN_MAP = Map.of(
            1, Pattern.compile("(?=(one))"),
            2, Pattern.compile("(?=(two))"),
            3, Pattern.compile("(?=(three))"),
            4, Pattern.compile("(?=(four))"),
            5, Pattern.compile("(?=(five))"),
            6, Pattern.compile("(?=(six))"),
            7, Pattern.compile("(?=(seven))"),
            8, Pattern.compile("(?=(eight))"),
            9, Pattern.compile("(?=(nine))")
    );

    public Solver() {
        try {
            final List<String> data = FileUtil.readInputFile(this.getClass());
            Date start = new Date();
            System.out.println(solvePartOne(data));
            System.out.printf("First puzzle took %d ms\n", new Date().getTime()-start.getTime());
            start = new Date();
            System.out.println(solvePartTwo(data));
            System.out.printf("Second puzzle took %d ms\n", new Date().getTime()-start.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int solvePartOne(final List<String> data) {
        return data.stream()
                .mapToInt(Solver::solveLine)
                .sum();
    }

    static int solvePartTwo(final List<String> data) {
        return data.stream()
                .map(Solver::parseLine)
                .mapToInt(Solver::solveLine)
                .sum();
    }

    private static int solveLine(final String line) {
        final List<Integer> filteredDigits = line.chars()
                .filter(Character::isDigit)
                .map(c -> c - '0')
                .boxed()
                .toList();

        return filteredDigits.get(0) * 10 + filteredDigits.get(filteredDigits.size()-1);
    }

    private static String parseLine(final String line) {
        String result = line;
        for (final Integer digit : DIGIT_PATTERN_MAP.keySet()) {
            final int[] positions = findStringIntegerPositions(line, digit);
            result = replaceCharacterWithDigitInPlace(result, digit, positions);
        }

        return result;
    }

    private static int[] findStringIntegerPositions(final String line, final int digit) {
        return DIGIT_PATTERN_MAP.get(digit)
                .matcher(line)
                .results()
                .mapToInt(MatchResult::start)
                .toArray();
    }

    private static String replaceCharacterWithDigitInPlace(final String line, final int digit, final int[] positions) {
        final char[] chars = line.toCharArray();
        for (final int position : positions) {
            chars[position] = Character.forDigit(digit, 10);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        new Solver();
    }
}
