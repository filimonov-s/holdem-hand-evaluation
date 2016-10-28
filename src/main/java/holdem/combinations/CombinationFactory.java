package holdem.combinations;

import org.jetbrains.annotations.NotNull;
import holdem.card.Card;

import java.util.Set;

/**
 * @author s.filimonov
 */
public interface CombinationFactory {
    @NotNull Combination buildCombination(@NotNull Set<Card> cards);
}
