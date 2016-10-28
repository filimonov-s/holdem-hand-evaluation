package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.combinations.CombinationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author s.filimonov
 */
public interface HandEvaluator {

    @Nullable Set<Card> evaluate(@NotNull Set<Card> cards);

    @NotNull CombinationType getType();

}
