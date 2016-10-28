package holdem.combinations.evaluators;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import holdem.card.Card;
import holdem.combinations.CombinationType;

import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

/**
 * @author s.filimonov
 */
public final class HighCardEvaluator implements HandEvaluator {

    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        return cards.stream().sorted(comparing(Card::getRank).reversed()).limit(5).collect(toSet());
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.HIGH_CARD;
    }
}
