package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.card.Rank;
import holdem.card.Suit;
import holdem.combinations.CombinationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

/**
 * @author s.filimonov
 */
public final class FourOfAKindEvaluator implements HandEvaluator {

    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        for (Rank rank : Rank.valuesDesc()) {
            if (cards.stream().filter(card -> card.getRank() == rank).count() == 4) {
                Set<Card> hand = Arrays.stream(Suit.values()).map(suit -> Card.cardOf(rank, suit)).collect(toSet());
                hand.add(cards.stream()
                        .filter(card -> card.getRank() != rank)
                        .sorted(comparing(Card::getRank).reversed())
                        .findFirst().orElse(null)
                );
                return hand;
            }
        }
        return null;
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.FOUR_OF_A_KIND;
    }
}
