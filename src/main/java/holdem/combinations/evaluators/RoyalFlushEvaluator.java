package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.combinations.CombinationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static holdem.card.Rank.A;
import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

/**
 * @author s.filimonov
 */
public final class RoyalFlushEvaluator implements HandEvaluator {

    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        Card[] cardsArray = cards.stream().sorted(
                comparing(Card::getSuit).thenComparing(Card::getRank).reversed()
        ).toArray(Card[]::new);

        for (int i = 0; i < cardsArray.length; i++) {
            for (int j = i, sequenceCounter = 0; j < cardsArray.length; j++) {
                if (j == i) {
                    if (cardsArray[j].getRank() != A)
                        break;
                } else if (cardsArray[j].getRank().ordinal() + 1 != cardsArray[j - 1].getRank().ordinal()
                        || cardsArray[j].getSuit() != cardsArray[j - 1].getSuit()) {
                    break;
                }

                sequenceCounter++;
                if (sequenceCounter == 5) {
                    return stream(cardsArray).skip(i).limit(5).collect(toSet());
                }
            }
        }
        return null;
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.ROYAL_FLUSH;
    }
}
