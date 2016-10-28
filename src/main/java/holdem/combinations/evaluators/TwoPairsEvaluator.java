package holdem.combinations.evaluators;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import holdem.card.Card;
import holdem.card.Rank;
import holdem.combinations.CombinationType;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

/**
 * @author s.filimonov
 */
public final class TwoPairsEvaluator implements HandEvaluator {

    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        for (Rank _1st_pairRank : Rank.valuesDesc()) {
            if (cards.stream().filter(card -> card.getRank() == _1st_pairRank).count() == 2) {
                for (Rank _2nd_pairRank : Rank.valuesDesc()) {
                    if (_2nd_pairRank == _1st_pairRank)
                        continue;
                    if (cards.stream().filter(card -> card.getRank() == _2nd_pairRank).count() == 2) {
                        return Stream.concat(
                                cards.stream()
                                        .filter(card -> card.getRank() == _1st_pairRank || card.getRank() == _2nd_pairRank),
                                cards.stream()
                                        .filter(card -> card.getRank() != _1st_pairRank && card.getRank() != _2nd_pairRank)
                                        .sorted(comparing(Card::getRank).reversed()).limit(1)
                        ).collect(toSet());
                    }
                }
            }
        }
        return null;
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.TWO_PAIRS;
    }
}
