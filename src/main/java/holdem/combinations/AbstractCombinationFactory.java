package holdem.combinations;

import holdem.combinations.evaluators.HandEvaluator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import holdem.card.Card;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

/**
 * @author s.filimonov
 */
public abstract class AbstractCombinationFactory implements CombinationFactory {

    private final List<HandEvaluator> evaluators;

    AbstractCombinationFactory(@NotNull List<HandEvaluator> evaluators,
                               @NotNull Comparator<HandEvaluator> evaluatorsPriorityComparator) {
        this.evaluators = unmodifiableList(evaluators.stream().sorted(evaluatorsPriorityComparator).collect(toList()));
    }

    @Override
    public @NotNull Combination buildCombination(@NotNull Set<Card> cards) {
        for (HandEvaluator evaluator : evaluators) {
            @Nullable Set<Card> hand = evaluator.evaluate(cards);
            if (hand != null) {
                return new Combination(evaluator.getType(), hand);
            }
        }
        throw new AssertionError("The combination must be defined for any valid set of cards. Input set: " + cards);
    }
}
