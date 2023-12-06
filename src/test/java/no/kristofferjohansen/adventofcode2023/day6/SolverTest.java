package no.kristofferjohansen.adventofcode2023.day6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void solvePartOne() {
        final String data = """
                Time:      7  15   30
                Distance:  9  40  200""";

        assertEquals(288, Solver.solvePartOne(data.lines().toList()));
    }
}