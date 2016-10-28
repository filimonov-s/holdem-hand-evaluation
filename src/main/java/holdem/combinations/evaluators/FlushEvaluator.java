package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.card.Suit;
import holdem.combinations.CombinationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

/**
 * @author s.filimonov
 */
public final class FlushEvaluator implements HandEvaluator {

    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        Optional<Map.Entry<Suit, List<Card>>> sameSuitCards = cards
                .stream().collect(groupingBy(Card::getSuit)).entrySet()
                .stream().filter(entry -> entry.getValue().size() >= 5).findFirst();

        if (!sameSuitCards.isPresent())
            return null;

        return sameSuitCards.get().getValue().stream().sorted(comparing(Card::getRank).reversed()).limit(5).collect(toSet());
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.FLUSH;
    }
}
