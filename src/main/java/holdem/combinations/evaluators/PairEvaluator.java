package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.card.Rank;
import holdem.combinations.CombinationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

/**
 * @author s.filimonov
 */
public class PairEvaluator implements HandEvaluator {

    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        for (Rank rank : Rank.valuesDesc()) {
            if (cards.stream().filter(card -> card.getRank() == rank).count() == 2) {
                return Stream.concat(
                        cards.stream()
                                .filter(card -> card.getRank() == rank),
                        cards.stream()
                                .filter(card -> card.getRank() != rank)
                                .sorted(comparing(Card::getRank).reversed())
                                .limit(3)
                ).collect(toSet());
            }
        }
        return null;
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.PAIR;
    }
}
