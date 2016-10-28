package holdem.combinations;

import holdem.card.Card;
import holdem.card.Rank;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * @author s.filimonov
 */
public enum CombinationType {
    HIGH_CARD(Comparators.compareByFirstHigherCard),
    PAIR(Comparators.compareByNumberOfEquallyRankedCards),
    TWO_PAIRS(Comparators.compareByNumberOfEquallyRankedCards),
    THREE_OF_A_KIND(Comparators.compareByNumberOfEquallyRankedCards),
    STRAIGHT((o1, o2) -> {
        boolean _1containsAceAndTwo = containsAceAndTwo(o1);
        boolean _2containsAceAndTwo = containsAceAndTwo(o2);
        if (_1containsAceAndTwo && _2containsAceAndTwo)
            return 0;
        if (_1containsAceAndTwo)
            return -1;
        if (_2containsAceAndTwo)
            return 1;
        return Comparators.compareHighestCard.compare(o1, o2);
    }),
    FLUSH(Comparators.compareByFirstHigherCard),
    FULL_HOUSE(Comparators.compareByNumberOfEquallyRankedCards),
    FOUR_OF_A_KIND(Comparators.compareByNumberOfEquallyRankedCards),
    STRAIGHT_FLUSH(STRAIGHT.sameTypeComparator),
    @SuppressWarnings("ComparatorMethodParameterNotUsed")
    ROYAL_FLUSH((o1, o2) -> {
        throw new AssertionError("Should never compare two Royal Flushes");
    });

    final Comparator<Set<Card>> sameTypeComparator;

    private static class Comparators {

        private static Map<Rank, Long> groupedByAndOrderedByNumberAndRank(Set<Card> cards) {
            Map<Rank, Long> map = new LinkedHashMap<>();
            cards.stream()
                    .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()))
                    .entrySet().stream()
                    .sorted(comparing(Map.Entry<Rank, Long>::getValue).thenComparing(Map.Entry::getKey).reversed())
                    .forEachOrdered(c -> map.put(c.getKey(), c.getValue()));
            return map;
        }

        private static final Comparator<Set<Card>> compareByNumberOfEquallyRankedCards = (o1, o2) -> {
            Map<Rank, Long> c1 = groupedByAndOrderedByNumberAndRank(o1);
            Map<Rank, Long> c2 = groupedByAndOrderedByNumberAndRank(o2);

            Iterator<Map.Entry<Rank, Long>> i1 = c1.entrySet().iterator();
            Iterator<Map.Entry<Rank, Long>> i2 = c2.entrySet().iterator();

            while (i1.hasNext() && i2.hasNext()) {
                Map.Entry<Rank, Long> next1 = i1.next();
                Map.Entry<Rank, Long> next2 = i2.next();
                int compareTo = Comparator.comparing(Map.Entry<Rank, Long>::getKey).compare(next1, next2);
                if (compareTo != 0) {
                    return compareTo;
                }
            }
            return 0;
        };

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        private static final Comparator<Set<Card>> compareHighestCard = (o1, o2) -> {
            Card highestCard1 = o1.stream().sorted(comparing(Card::getRank)).findFirst().get();
            Card highestCard2 = o2.stream().sorted(comparing(Card::getRank)).findFirst().get();
            return highestCard1.getRank().compareTo(highestCard2.getRank());
        };

        private static final Comparator<Set<Card>> compareByFirstHigherCard = (o1, o2) -> {
            Iterator<Card> i1 = o1.stream().sorted(comparing(Card::getRank)).iterator();
            Iterator<Card> i2 = o2.stream().sorted(comparing(Card::getRank)).iterator();
            while (i1.hasNext() && i2.hasNext()) {
                Card next1 = i1.next();
                Card next2 = i2.next();
                int compareRanks = next1.getRank().compareTo(next2.getRank());
                if (compareRanks != 0) {
                    return compareRanks;
                }
            }
            return 0;
        };
    }

    private static boolean containsAceAndTwo(Set<Card> cards) {
        return cards.stream().map(Card::getRank).collect(Collectors.toList()).containsAll(Arrays.asList(Rank._2, Rank.A));
    }


    CombinationType(Comparator<Set<Card>> sameTypeComparator) {
        this.sameTypeComparator = sameTypeComparator;
    }
}
