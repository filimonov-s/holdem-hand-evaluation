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
public class FourOfAKindEvaluatorTest {

    private FourOfAKindEvaluator fourOfAKindEvaluator = new FourOfAKindEvaluator();

    @Parameters(name = "{index}: Four cardOf a kind={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{false, null, null, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, A, _10, asList(
                        cardOf(A, HEART), cardOf(A, DIAMOND), cardOf(A, CLUB), cardOf(A, SPADE), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, K, _10, asList(
                        cardOf(K, HEART), cardOf(K, DIAMOND), cardOf(K, CLUB), cardOf(K, SPADE), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, Q, _10, asList(
                        cardOf(Q, HEART), cardOf(Q, DIAMOND), cardOf(Q, CLUB), cardOf(Q, SPADE), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, J, _10, asList(
                        cardOf(J, HEART), cardOf(J, DIAMOND), cardOf(J, CLUB), cardOf(J, SPADE), cardOf(_10, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, _10, A, asList(
                        cardOf(_10, HEART), cardOf(_10, DIAMOND), cardOf(_10, CLUB), cardOf(_10, SPADE), cardOf(A, DIAMOND), cardOf(_9, DIAMOND), cardOf(_8, DIAMOND))},
                new Object[]{true, _9, K, asList(
                        cardOf(_9, HEART), cardOf(_9, DIAMOND), cardOf(_9, CLUB), cardOf(_9, SPADE), cardOf(_10, DIAMOND), cardOf(K, DIAMOND), cardOf(_8, DIAMOND))}

        );
    }

    private boolean hasFourOfAKind;
    private Rank _4_cardsRank;
    private Rank _1_cardRank;
    private Set<Card> cards;

    public FourOfAKindEvaluatorTest(boolean hasFourOfAKind, Rank _4_cardsRank, Rank _1_cardRank, List<Card> cards) {
        this.hasFourOfAKind = hasFourOfAKind;
        this._4_cardsRank = _4_cardsRank;
        this._1_cardRank = _1_cardRank;

        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() throws Exception {
        @Nullable Set<Card> hand = fourOfAKindEvaluator.evaluate(this.cards);

        if (hasFourOfAKind) {
            assertNotNull(hand);
            assertEquals(4, hand.stream().filter(card -> card.getRank() == _4_cardsRank).count());
            assertEquals(1, hand.stream().filter(card -> card.getRank() == _1_cardRank).count());
        } else {
            assertNull(hand);
        }


    }

}