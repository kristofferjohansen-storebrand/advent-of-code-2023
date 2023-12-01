package no.kristofferjohansen.adventofcode2023.common;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileUtil {
    public static List<String> readInputFile(Class<?> callingClass) throws Exception {
        return readInputFileGeneric(callingClass, "input.txt");
    }

    public static List<String> readInputFile(Class<?> callingClass, String delimiter) throws Exception {
        return readInputFileGeneric(callingClass, "input.txt", delimiter);
    }

    public static List<String> readInputFileGeneric(Class<?> callingClass, String fileName) throws Exception {
        List<String> lines;

        URI uri = Objects.requireNonNull(callingClass.getResource(fileName)).toURI();
        lines = Files.readAllLines(Paths.get(uri), StandardCharsets.UTF_8);

        return lines;
    }

    public static List<String> readInputFileGeneric(Class<?> callingClass, String fileName, String delimiter) throws Exception {
        List<String> lines = readInputFileGeneric(callingClass, fileName);

        return Arrays.asList(lines.get(0).split(delimiter));
    }
}