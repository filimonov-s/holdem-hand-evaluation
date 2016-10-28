package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.card.Rank;
import holdem.card.Suit;
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
public class StraightFlushEvaluatorTest {

    private StraightEvaluator straightEvaluator = new StraightEvaluator();

    @Parameters(name = "{index}: Straight={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{true, K, HEART, asList(
                        cardOf(_2, HEART), cardOf(K, HEART), cardOf(Q, HEART), cardOf(J, HEART), cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, HEART))},
                new Object[]{true, _10, HEART, asList(
                        cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, HEART), cardOf(_7, HEART), cardOf(_6, HEART), cardOf(_3, DIAMOND), cardOf(_2, DIAMOND))},
                new Object[]{true, _10, HEART, asList(
                        cardOf(_10, DIAMOND), cardOf(_10, SPADE), cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, HEART), cardOf(_7, HEART), cardOf(_6, HEART))},
                new Object[]{true, A, DIAMOND, asList(
                        cardOf(A, DIAMOND), cardOf(_2, SPADE), cardOf(_3, DIAMOND), cardOf(_4, DIAMOND), cardOf(_5, DIAMOND), cardOf(_7, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, _6, DIAMOND, asList(
                        cardOf(A, DIAMOND), cardOf(_2, DIAMOND), cardOf(_3, DIAMOND), cardOf(_4, DIAMOND), cardOf(_5, DIAMOND), cardOf(_6, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{false, null, null, asList(
                        cardOf(A, DIAMOND), cardOf(K, DIAMOND), cardOf(Q, DIAMOND), cardOf(_10, DIAMOND), cardOf(_10, HEART), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{false, null, null, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, HEART), cardOf(Q, SPADE), cardOf(_9, HEART), cardOf(_8, HEART), cardOf(_7, HEART))}
        );
    }

    private boolean hasStraightFlush;
    private Rank highestRank;
    private Suit suit;
    private Set<Card> cards;

    public StraightFlushEvaluatorTest(boolean hasStraightFlush, Rank highestRank, Suit suit, List<Card> cards) {
        this.hasStraightFlush = hasStraightFlush;
        this.highestRank = highestRank;
        this.suit = suit;
        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() throws Exception {
        @Nullable Set<Card> hand = straightEvaluator.evaluate(cards);

        if (hasStraightFlush) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertEquals(highestRank.ordinal(), hand.stream().mapToInt(card -> card.getRank().ordinal()).max().orElse(-1));
        } else {
            assertNull(hand);
        }
    }


}