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
public class ThreeOfAKindEvaluatorTest {

    private ThreeOfAKindEvaluator threeOfAKindEvaluator = new ThreeOfAKindEvaluator();

    @Parameters(name = "{index}: Three cardOf a kind={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{false, null, null, null, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{false, null, null, null, asList(
                        cardOf(_9, HEART), cardOf(_9, DIAMOND), cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_10, DIAMOND), cardOf(_10, SPADE), cardOf(_8, DIAMOND))},
                new Object[]{true, A, K, J, asList(
                        cardOf(A, HEART), cardOf(A, DIAMOND), cardOf(A, CLUB), cardOf(K, SPADE), cardOf(J, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, K, J, _10, asList(
                        cardOf(K, HEART), cardOf(K, DIAMOND), cardOf(K, CLUB), cardOf(J, SPADE), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, Q, _10, _9, asList(
                        cardOf(Q, HEART), cardOf(Q, DIAMOND), cardOf(Q, CLUB), cardOf(_10, SPADE), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND), cardOf(_2, DIAMOND))},
                new Object[]{true, J, A, Q, asList(
                        cardOf(J, HEART), cardOf(J, DIAMOND), cardOf(J, CLUB), cardOf(A, SPADE), cardOf(Q, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, _10, A, _4, asList(
                        cardOf(_10, HEART), cardOf(_10, DIAMOND), cardOf(_10, CLUB), cardOf(A, SPADE), cardOf(_4, DIAMOND), cardOf(_3, DIAMOND), cardOf(_2, DIAMOND))}
        );
    }

    private boolean hasFullHouse;
    private Rank _3_cardsRank;
    private Rank _1st_highCardRank;
    private Rank _2nd_highCardRank;
    private Set<Card> cards;

    public ThreeOfAKindEvaluatorTest(boolean hasFullHouse, Rank _3_cardsRank, Rank _1st_highCardRank, Rank _2nd_highCardRank,
                                     List<Card> cards) {
        this.hasFullHouse = hasFullHouse;
        this._3_cardsRank = _3_cardsRank;
        this._1st_highCardRank = _1st_highCardRank;
        this._2nd_highCardRank = _2nd_highCardRank;

        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() {
        @Nullable Set<Card> hand = threeOfAKindEvaluator.evaluate(cards);

        if (hasFullHouse) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertEquals(3, hand.stream().filter(card -> card.getRank() == _3_cardsRank).count());
            assertTrue(hand.stream().filter(card -> card.getRank() == _1st_highCardRank).findFirst().isPresent());
            assertTrue(hand.stream().filter(card -> card.getRank() == _2nd_highCardRank).findFirst().isPresent());
        } else {
            assertNull(hand);
        }
    }
}