package holdem.combinations;


import holdem.combinations.evaluators.*;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

/**
 * @author s.filimonov
 */
public final class HoldemCombinationFactory extends AbstractCombinationFactory {

    public HoldemCombinationFactory() {
        super(
                asList(
                        new FlushEvaluator(),
                        new FourOfAKindEvaluator(),
                        new FullHouseEvaluator(),
                        new HighCardEvaluator(),
                        new PairEvaluator(),
                        new RoyalFlushEvaluator(),
                        new StraightFlushEvaluator(),
                        new StraightEvaluator(),
                        new ThreeOfAKindEvaluator(),
                        new TwoPairsEvaluator()
                ),
                comparing(HandEvaluator::getType).reversed());
    }
}
