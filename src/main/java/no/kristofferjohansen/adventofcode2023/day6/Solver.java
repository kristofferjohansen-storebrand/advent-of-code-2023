package no.kristofferjohansen.adventofcode2023.day6;

import no.kristofferjohansen.adventofcode2023.common.FileUtil;
import no.kristofferjohansen.adventofcode2023.common.ParseUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class Solver {

    public Solver() {
        try {
            final List<String> data = FileUtil.readInputFile(this.getClass());
            Date start = new Date();
            System.out.println(solvePartOne(data));
            System.out.printf("First puzzle took %d ms\n", new Date().getTime() - start.getTime());
//             start = new Date();
//             System.out.println(solvePartTwo(data));
//             System.out.printf("Second puzzle took %d ms\n", new Date().getTime()-start.getTime());
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    static int solvePartOne(final List<String> data) {
        final String raceTimeLine = data.get(0);
        final int[] raceTimes = ParseUtil.parseToIntArray(raceTimeLine.substring(raceTimeLine.indexOf(":")+1));
        final String raceDistanceLine = data.get(1);
        final int[] raceDistances = ParseUtil.parseToIntArray(raceDistanceLine.substring(raceDistanceLine.indexOf(":")+1));

        return IntStream.range(0, raceTimes.length)
                .map(raceIndex -> IntStream.range(0, raceTimes[raceIndex])
                        .map(holdingTime -> getScoreForBoat(raceTimes[raceIndex] - holdingTime, holdingTime, raceDistances[raceIndex]))
                        .sum())
                .reduce(1, (a, b) -> a * b);
    }

    static int getScoreForBoat(final int timeLeft, final int speed, final int distanceToBeat) {
        if (timeLeft == 0 || speed == 0) {
            return 0;
        }

        return timeLeft*speed > distanceToBeat ? 1 : 0;
    }

    public static void main(String[] args) {
        new Solver();
    }
}
