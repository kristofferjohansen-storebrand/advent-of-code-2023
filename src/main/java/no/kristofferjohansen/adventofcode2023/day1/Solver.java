package no.kristofferjohansen.adventofcode2023.day1;

import no.kristofferjohansen.adventofcode2023.common.FileUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Solver {

    public Solver() {
        try {
            final List<String> data = FileUtil.readInputFile(this.getClass());
            Date start = new Date();
            System.out.println(solvePartOne(data));
            System.out.printf("First puzzle took %d ms\n", new Date().getTime()-start.getTime());
//            start = new Date();
//            System.out.println(solveSecond(values));
//            System.out.printf("Second puzzle took %d ms\n", new Date().getTime()-start.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int solvePartOne(final List<String> data) {
        return data.stream()
                .mapToInt(Solver::solveLine)
                .sum();
    }

    private static int solveLine(final String line) {
        final List<Integer> filteredDigits = line.chars()
                .filter(Character::isDigit)
                .map(c -> c- '0')
                .boxed()
                .toList();

        return filteredDigits.get(0) * 10 + filteredDigits.get(filteredDigits.size()-1);
    }

    public static void main(String[] args) {
        new Solver();
    }
}
