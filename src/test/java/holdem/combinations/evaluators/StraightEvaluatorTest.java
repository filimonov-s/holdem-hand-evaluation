package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.card.Rank;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static holdem.card.Card.cardOf;
import static holdem.card.Rank.*;
import static holdem.card.Suit.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * @author s.filimonov
 */
@RunWith(Parameterized.class)
public class StraightEvaluatorTest {

    private StraightEvaluator straightEvaluator = new StraightEvaluator();

    @Parameters(name = "{index}: Straight={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{true, A, asList(
                        cardOf(A, HEART), cardOf(K, CLUB), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_10, SPADE), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, _10, asList(
                        cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, DIAMOND), cardOf(_7, DIAMOND), cardOf(_6, DIAMOND), cardOf(_3, DIAMOND), cardOf(_2, DIAMOND))},
                new Object[]{true, _10, asList(
                        cardOf(_10, DIAMOND), cardOf(_10, SPADE), cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, DIAMOND), cardOf(_7, DIAMOND), cardOf(_6, DIAMOND))},
                new Object[]{true, A, asList(
                        cardOf(A, DIAMOND), cardOf(_2, SPADE), cardOf(_3, HEART), cardOf(_4, HEART), cardOf(_5, DIAMOND), cardOf(_7, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, _6, asList(
                        cardOf(A, DIAMOND), cardOf(_2, SPADE), cardOf(_3, HEART), cardOf(_4, HEART), cardOf(_5, DIAMOND), cardOf(_6, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{false, null, asList(
                        cardOf(A, HEART), cardOf(K, DIAMOND), cardOf(Q, HEART), cardOf(_10, CLUB), cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, HEART))},
                new Object[]{false, null, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, HEART), cardOf(Q, SPADE), cardOf(_9, HEART), cardOf(_8, HEART), cardOf(_7, HEART))},
                new Object[]{false, null, asList(
                        cardOf(_2, CLUB), cardOf(_2, DIAMOND), cardOf(_3, CLUB), cardOf(_4, DIAMOND), cardOf(J, DIAMOND), cardOf(Q, DIAMOND), cardOf(A, SPADE))}
        );
    }

    private boolean hasStraight;
    private Rank highestRank;
    private Set<Card> cards;

    public StraightEvaluatorTest(boolean hasStraight, Rank highestRank, List<Card> cards) {
        this.hasStraight = hasStraight;
        this.highestRank = highestRank;
        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() throws Exception {
        @Nullable Set<Card> hand = straightEvaluator.evaluate(cards);

        if (hasStraight) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertEquals(highestRank, Rank.values()[hand.stream().mapToInt(card -> card.getRank().ordinal()).max().orElse(-1)]);
        } else {
            assertNull(hand);
        }
    }


}