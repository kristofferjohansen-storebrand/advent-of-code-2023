package no.kristofferjohansen.adventofcode2023.day3;

import no.kristofferjohansen.adventofcode2023.common.FileUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solver {
    private static final Set<Character> DIGIT_MAP = Set.of('1', '2', '3', '4', '5', '6', '7', '8', '9');
    private static final Set<Character> NON_SYMBOL_MAP = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.');

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("([^\\d.])");

    public Solver() {
        try {
            final List<String> data = FileUtil.readInputFile(this.getClass());
            Date start = new Date();
            System.out.println(solvePartOne(data));
            System.out.printf("First puzzle took %d ms\n", new Date().getTime() - start.getTime());
//            start = new Date();
//            System.out.println(solvePartTwo(data));
//            System.out.printf("Second puzzle took %d ms\n", new Date().getTime()-start.getTime());
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static int solvePartOne(final List<String> data) {
        final List<Symbol> symbols = new ArrayList<>();
        final List<EnginePart> engineParts = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            parseLine(data.get(i), i, symbols, engineParts);
        }

        final Map<Integer, Map<Integer, Symbol>> symbolMap = symbols.stream()
                .collect(Collectors.groupingBy(Symbol::x, Collectors.toMap(Symbol::y, symbol -> symbol)));

        return engineParts.stream()
                .filter(enginePart -> isSymbolAdjacent(enginePart, data.get(0).length(), data.size(), symbolMap))
                .mapToInt(EnginePart::getValue)
                .sum();
    }

    private static void parseLine(final String line, final int y, final List<Symbol> symbols, final List<EnginePart> engineParts) {
        final Matcher numberMatcher = NUMBER_PATTERN.matcher(line);
        while (numberMatcher.find()) {
            engineParts.add(new EnginePart(numberMatcher.start(), y, Integer.parseInt(numberMatcher.group())));
        }

        final Matcher symbolMatcher = SYMBOL_PATTERN.matcher(line);
        while (symbolMatcher.find()) {
            symbols.add(new Symbol(symbolMatcher.start(), y));
        }
    }

    private static boolean isSymbolAdjacent(final EnginePart enginePart, final int maxX, final int maxY, final Map<Integer, Map<Integer, Symbol>> symbolMap) {
        final int startX = Math.max(0, enginePart.getX() - 1);
        final int endX = Math.min(maxX, enginePart.getX() + enginePart.getLength());
        final int startY = Math.max(0, enginePart.getY() - 1);
        final int endY = Math.min(maxY, enginePart.getY() + 1);

        return symbolMap.entrySet().stream()
                .filter(xSymbols -> xSymbols.getKey() >= startX && xSymbols.getKey() <= endX)
                .flatMap(xSymbols -> xSymbols.getValue().entrySet().stream())
                .anyMatch(ySymbol -> ySymbol.getKey() >= startY && ySymbol.getKey() <= endY);
    }

    public static void main(final String[] args) {
        new Solver();
    }

    record Symbol(int x, int y) {
    }

    static class EnginePart {
        private final int x;
        private final int y;
        private final int value;
        private final int length;

        public EnginePart(final int x, final int y, final int value) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.length = (int) Math.log10(value) + 1;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getValue() {
            return value;
        }

        public int getLength() {
            return length;
        }
    }
}
