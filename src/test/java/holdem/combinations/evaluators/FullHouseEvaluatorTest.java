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
public class FullHouseEvaluatorTest {

    private FullHouseEvaluator fullHouseEvaluator = new FullHouseEvaluator();

    @Parameters(name = "{index}: Full house={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{false, null, null, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{false, null, null, asList(
                        cardOf(_9, HEART), cardOf(_9, DIAMOND), cardOf(_9, CLUB), cardOf(_9, SPADE), cardOf(_10, DIAMOND), cardOf(_10, SPADE), cardOf(_8, DIAMOND))},
                new Object[]{true, A, K, asList(
                        cardOf(A, HEART), cardOf(A, DIAMOND), cardOf(A, CLUB), cardOf(K, SPADE), cardOf(K, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, K, J, asList(
                        cardOf(K, HEART), cardOf(K, DIAMOND), cardOf(K, CLUB), cardOf(J, SPADE), cardOf(J, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, Q, _10, asList(
                        cardOf(Q, HEART), cardOf(Q, DIAMOND), cardOf(Q, CLUB), cardOf(_10, SPADE), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, J, A, asList(
                        cardOf(J, HEART), cardOf(J, DIAMOND), cardOf(J, CLUB), cardOf(A, SPADE), cardOf(A, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, _10, _2, asList(
                        cardOf(_10, HEART), cardOf(_10, DIAMOND), cardOf(_10, CLUB), cardOf(_2, SPADE), cardOf(_2, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))}
        );
    }

    private boolean hasFullHouse;
    private Rank _3_cardsRank;
    private Rank _2_cardsRank;
    private Set<Card> cards;

    public FullHouseEvaluatorTest(boolean hasFullHouse, Rank _3_cardsRank, Rank _2_cardsRank, List<Card> cards) {
        this.hasFullHouse = hasFullHouse;
        this._3_cardsRank = _3_cardsRank;
        this._2_cardsRank = _2_cardsRank;

        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() {
        @Nullable Set<Card> hand = fullHouseEvaluator.evaluate(cards);

        if (hasFullHouse) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertEquals(3, hand.stream().filter(card -> card.getRank() == _3_cardsRank).count());
            assertEquals(2, hand.stream().filter(card -> card.getRank() == _2_cardsRank).count());
        } else {
            assertNull(hand);
        }
    }
}