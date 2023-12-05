package no.kristofferjohansen.adventofcode2023.day5;

import no.kristofferjohansen.adventofcode2023.common.FileUtil;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Solver {

    private enum MAP_TYPE {
        SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION
    }

    private static CategoryEntry DEFAULT_CATEGORY_ENTRY = new CategoryEntry(0, 0, 0);

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
            System.out.println(e);
        }
    }

    static Long solvePartOne(final List<String> data) {
        final List<List<CategoryEntry>> categoryMapList = parseData(data);
        return Stream.of(data.get(0).substring(7).split(" "))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .mapToLong(Long::parseLong)
                .map(value -> processValue(value, categoryMapList.listIterator()))
                .min().orElse(-1);
    }

//    private static List<Map<Long, Long>> parseData(final List<String> data) {
//        final int[] categoryDividerPositions = IntStream.range(0, data.size())
//                .filter(iter -> data.get(iter).isBlank())
//                .toArray();
//
//        return IntStream.range(0, categoryDividerPositions.length)
//                .mapToObj(categoryIndex -> parseCategory(data, categoryDividerPositions, categoryIndex))
//                .toList();
//    }

    private static List<List<CategoryEntry>> parseData(final List<String> data) {
        final int[] categoryDividerPositions = IntStream.range(0, data.size())
                .filter(iter -> data.get(iter).isBlank())
                .toArray();

        return IntStream.range(0, categoryDividerPositions.length)
                .mapToObj(categoryIndex -> parseCategory(data, categoryDividerPositions, categoryIndex))
                .toList();
    }

    private static List<CategoryEntry> parseCategory(final List<String> data, final int[] categoryDividerPositions, final int categoryIndex) {
        final int endOfCategoryIndex = categoryIndex == categoryDividerPositions.length - 1 ? data.size() : categoryDividerPositions[categoryIndex + 1];
        final List<String> categoryDataList = data.subList(categoryDividerPositions[categoryIndex]+2, endOfCategoryIndex);

        return categoryDataList.stream()
                .map(categoryDataLine -> Stream.of(categoryDataLine.split(" +"))
                        .map(String::trim)
                        .filter(value -> !value.isEmpty())
                        .mapToLong(Long::parseLong).toArray())
                .map(categoryMapping -> new CategoryEntry(categoryMapping[1], categoryMapping[0], categoryMapping[2]))
                .toList();
    }

//    private static Map<Long, Long> parseCategory(final List<String> data, final int[] categoryDividerPositions, final int categoryIndex) {
//        final int endOfCategoryIndex = categoryIndex == categoryDividerPositions.length - 1 ? data.size() : categoryDividerPositions[categoryIndex + 1];
//        final List<String> categoryDataList = data.subList(categoryDividerPositions[categoryIndex]+2, endOfCategoryIndex);
//
//        final Map<Long, Long> categoryDataMap = new HashMap<>();
//        categoryDataList.stream()
//                .map(categoryDataLine -> Stream.of(categoryDataLine.split(" +"))
//                        .map(String::trim)
//                        .filter(value -> !value.isEmpty())
//                        .mapToLong(Long::parseLong).toArray())
//                .forEach(categoryMapping -> addCategoryDataToMap(categoryDataMap, categoryMapping));
//
//        return categoryDataMap;
//    }

    private static Long processValue(final Long value, final ListIterator<Map<Long, Long>> categoryDataMapIterator) {
        if (categoryDataMapIterator.hasNext()) {
            return processValue(categoryDataMapIterator.next().getOrDefault(value, value), categoryDataMapIterator);
        }

        return value;
    }

    private static long processValue(final long value, final ListIterator<List<CategoryEntry>> categoryDataListIterator) {
        final List<CategoryEntry> categoryEntries = categoryDataListIterator.next();
        final CategoryEntry closestCategoryEntry = findMatchingCategoryEntry(value, categoryEntries);
        final long convertedValue = closestCategoryEntry.convertValue(value);
        if (categoryDataListIterator.hasNext()) {
            return processValue(convertedValue, categoryDataListIterator);
        }

        return convertedValue;
    }

    private static CategoryEntry findMatchingCategoryEntry(final long value, final List<CategoryEntry> categoryEntries) {
        return categoryEntries.stream()
                .filter(categoryEntry -> value >= categoryEntry.getSourceRange() && value < categoryEntry.getSourceRange() + categoryEntry.getMappingLength())
                .findFirst().orElse(DEFAULT_CATEGORY_ENTRY);
    }

    private static void addCategoryDataToMap(final Map<Long, Long> categoryDataMap, final long[] categoryMapping) {
        LongStream.range(0, categoryMapping[2])
                .forEach(iter -> categoryDataMap.put(categoryMapping[1] + iter, categoryMapping[0] + iter));
    }


    public static void main(String[] args) {
        new Solver();
    }
}

class CategoryEntry {
    private final long sourceRange;
    private final long destinationRange;
    private final long mappingLength;

    public CategoryEntry(final long sourceRange, final long destinationRange, final long mappingLength) {
        this.sourceRange = sourceRange;
        this.destinationRange = destinationRange;
        this.mappingLength = mappingLength;
    }

    public long getSourceRange() {
        return sourceRange;
    }

    public long getMappingLength() {
        return mappingLength;
    }

    public long convertValue(final long input) {
        return mappingLength == 0 ? input : destinationRange + (input - sourceRange);
    }
}