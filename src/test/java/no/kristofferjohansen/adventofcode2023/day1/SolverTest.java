package no.kristofferjohansen.adventofcode2023.day1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void testSolvePartOne() {
        final String data = """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet""";

        Assertions.assertEquals(142, Solver.solvePartOne(Arrays.asList(data.split("\n"))));
    }

    @Test
    void testSolvePartTwo() {
        final String data = """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen""";

        Assertions.assertEquals(281, Solver.solvePartTwo(Arrays.asList(data.split("\n"))));
    }
}