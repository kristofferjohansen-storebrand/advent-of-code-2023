package no.kristofferjohansen.adventofcode2023.day3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void solvePartOne() {
        final String data = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""";

        assertEquals(4361, Solver.solvePartOne(data.lines().toList()));
    }

    @Test
    void solvePartOneNoneAreAdjacent() {
        final String data = """
                .****.....
                *....*....
                *.12.*....
                *....*....
                .****.....""";

        assertEquals(0, Solver.solvePartOne(data.lines().toList()));
    }

    @Test
    void solvePartTwo() {
        final String data = """
                467..114..
                ...*......
                ..35..633.
                ......#...
                617*......
                .....+.58.
                ..592.....
                ......755.
                ...$.*....
                .664.598..""";

        assertEquals(467835, Solver.solvePartTwo(data.lines().toList()));
    }
}