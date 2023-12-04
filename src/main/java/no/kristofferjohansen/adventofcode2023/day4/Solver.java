package no.kristofferjohansen.adventofcode2023.day4;

import no.kristofferjohansen.adventofcode2023.common.FileUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solver {

    private static final Pattern CARD_ID_PATTERN = Pattern.compile("^Card +(\\d+):");

    public Solver() {
        try {
             final List<String> data = FileUtil.readInputFile(this.getClass());
             Date start = new Date();
             System.out.println(solvePartOne(data));
             System.out.printf("First puzzle took %d ms\n", new Date().getTime() - start.getTime());
            // start = new Date();
            // System.out.println(solvePartTwo(data));
            // System.out.printf("Second puzzle took %d ms\n", new Date().getTime()-start.getTime());
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    static int solvePartOne(final List<String> data) {
        return data.stream()
                .map(Solver::parseData)
                .mapToInt(Solver::getCardScore)
                .filter(score -> score >= 1)
                .sum();
    }

    private static ScratchCard parseData(final String cardData) {
        final int cardIdDividerPosition = cardData.indexOf(':');
        final int cardNumberDividerPosition = cardData.indexOf('|');
        final int cardId = getCardId(cardData);

        final Set<Integer> winningNumbers = Stream.of(cardData.substring(cardIdDividerPosition+1, cardNumberDividerPosition-1).split(" +"))
                .map(String::trim)
                .filter(cardNumber -> !cardNumber.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        final Set<Integer> cardNumbers = Stream.of(cardData.substring(cardNumberDividerPosition+1).split(" +"))
                .map(String::trim)
                .filter(cardNumber -> !cardNumber.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toSet());

        return new ScratchCard(cardId, winningNumbers, cardNumbers);
    }

    private static int getCardScore(final ScratchCard scratchCard) {
        return (int) Math.pow(2, scratchCard.getWinningNumbers().stream()
                .filter(scratchCard.getCardNumbers()::contains)
                .count() - 1);
    }

    private static int getCardId(final String cardData) {
        final Matcher matcher = CARD_ID_PATTERN.matcher(cardData);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }

    public static void main(final String[] args) {
        new Solver();
    }
}

class ScratchCard {
    private final int cardId;
    private final Set<Integer> winningNumbers;
    private final Set<Integer> cardNumbers;

    public ScratchCard(final int cardId, final Set<Integer> winningNumbers, final Set<Integer> cardNumbers) {
        this.cardId = cardId;
        this.winningNumbers = winningNumbers;
        this.cardNumbers = cardNumbers;
    }

    public int getCardId() {
        return cardId;
    }

    public Set<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public Set<Integer> getCardNumbers() {
        return cardNumbers;
    }
}
