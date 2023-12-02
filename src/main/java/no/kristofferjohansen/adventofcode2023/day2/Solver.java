package no.kristofferjohansen.adventofcode2023.day2;

import no.kristofferjohansen.adventofcode2023.common.FileUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solver {

    public Solver() {
        try {
            final List<String> data = FileUtil.readInputFile(this.getClass());
            Date start = new Date();
            System.out.println(solvePartOne(data));
            System.out.printf("First puzzle took %d ms\n", new Date().getTime()-start.getTime());
//            start = new Date();
//            System.out.println(solvePartTwo(data));
//            System.out.printf("Second puzzle took %d ms\n", new Date().getTime()-start.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int solvePartOne(final List<String> data) {
        return data.stream()
                .mapToInt(gameData -> checkGame(gameData, 13, 14, 12))
                .sum();
    }

    private static int checkGame(final String gameData, final int greenMax, final int blueMax, final int redMax) {
        final int gameDataDividerIndex = gameData.indexOf(":");
        final String gameSetsString = gameData.substring(gameDataDividerIndex + 1).trim();
        final String[] gameSets = gameSetsString.split(Pattern.quote(";"));

        return Arrays.stream(gameSets)
                .map(Solver::buildCubeAmountMap)
                .anyMatch(cubeAmountMap -> cubeAmountMap.get("green") > greenMax
                        || cubeAmountMap.get("blue") > blueMax
                        || cubeAmountMap.get("red") > redMax)? 0 : Integer.parseInt(gameData.substring(5, gameDataDividerIndex));
    }

    private static Map<String, Integer> buildCubeAmountMap(final String setData) {

        final Map<String, Integer> cubeAmountMap = Arrays.stream(setData.split(","))
                .map(cubeData -> cubeData.trim().split(" "))
                .collect(Collectors.toMap(cubeData -> cubeData[1], cubeData -> Integer.parseInt(cubeData[0])));

        cubeAmountMap.putIfAbsent("green", 0);
        cubeAmountMap.putIfAbsent("blue", 0);
        cubeAmountMap.putIfAbsent("red", 0);

        return cubeAmountMap;
    }

    static int solvePart2() {
        return 0;
    }

    public static void main(final String[] args) {
        new Solver();
    }
}
