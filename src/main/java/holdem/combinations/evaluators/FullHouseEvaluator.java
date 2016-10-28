package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.card.Rank;
import holdem.combinations.CombinationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * @author s.filimonov
 */
public final class FullHouseEvaluator implements HandEvaluator {

    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        for (Rank _3_cardsRank : Rank.valuesDesc()) {
            if (cards.stream().filter(card -> card.getRank() == _3_cardsRank).count() == 3) {
                for (Rank _2_cardsRank : Rank.valuesDesc()) {
                    long _2_cardsCount = cards.stream().filter(card -> card.getRank() == _2_cardsRank).count();
                    if (_3_cardsRank == _2_cardsRank)
                        continue;
                    if (_2_cardsCount == 2 || _2_cardsCount == 3) {
                        return Stream.concat(
                                cards.stream().filter(card -> card.getRank() == _3_cardsRank),
                                cards.stream().filter(card -> card.getRank() == _2_cardsRank).limit(2)
                        ).collect(toSet());
                    }
                }
            }
        }
        return null;
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.FULL_HOUSE;
    }
}
