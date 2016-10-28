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
public class TwoPairsEvaluatorTest {

    private TwoPairsEvaluator twoPairsEvaluator = new TwoPairsEvaluator();

    @Parameters(name = "{index}: Two pairs={0}")
    public static List<Object[]> data() {
        return asList(

                new Object[]{false, null, null, null, asList(
                        cardOf(A, HEART), cardOf(_2, DIAMOND), cardOf(_4, CLUB), cardOf(_6, SPADE), cardOf(_7, DIAMOND), cardOf(J, SPADE), cardOf(J, DIAMOND))},
                new Object[]{true, A, K, J, asList(
                        cardOf(A, HEART), cardOf(A, SPADE), cardOf(K, DIAMOND), cardOf(K, CLUB), cardOf(J, DIAMOND), cardOf(J, SPADE), cardOf(_10, DIAMOND))},
                new Object[]{true, K, _10, _9, asList(
                        cardOf(K, HEART), cardOf(K, DIAMOND), cardOf(_10, CLUB), cardOf(_10, SPADE), cardOf(_9, DIAMOND), cardOf(_9, SPADE), cardOf(_8, DIAMOND))},
                new Object[]{true, K, _2, Q, asList(
                        cardOf(K, HEART), cardOf(K, DIAMOND), cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_10, DIAMOND))}

        );
    }

    private boolean hasFullHouse;
    private Rank _1st_pairRank;
    private Rank _2nd_pairRank;
    private Rank highCardRank;
    private Set<Card> cards;

    public TwoPairsEvaluatorTest(boolean hasFullHouse, Rank _1st_pairRank, Rank _2nd_pairRank, Rank highCardRank,
                                 List<Card> cards) {
        this.hasFullHouse = hasFullHouse;
        this._1st_pairRank = _1st_pairRank;
        this._2nd_pairRank = _2nd_pairRank;
        this.highCardRank = highCardRank;

        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() {
        @Nullable Set<Card> hand = twoPairsEvaluator.evaluate(cards);

        if (hasFullHouse) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertEquals(2, hand.stream().filter(card -> card.getRank() == _1st_pairRank).count());
            assertEquals(2, hand.stream().filter(card -> card.getRank() == _2nd_pairRank).count());
            assertTrue(hand.stream().filter(card -> card.getRank() == highCardRank).findFirst().isPresent());
        } else {
            assertNull(hand);
        }
    }
}