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
public class PairEvaluatorTest {

    private PairEvaluator twoPairsEvaluator = new PairEvaluator();

    @Parameters(name = "{index}: Pair={0}")
    public static List<Object[]> data() {
        return asList(

                new Object[]{false, null, null, asList(
                        cardOf(A, HEART), cardOf(_2, DIAMOND), cardOf(_4, CLUB), cardOf(_6, SPADE), cardOf(_7, DIAMOND), cardOf(Q, SPADE), cardOf(J, DIAMOND))},
                new Object[]{true, A, asList(K, Q, _10), asList(
                        cardOf(A, HEART), cardOf(A, SPADE), cardOf(K, DIAMOND), cardOf(Q, CLUB), cardOf(_10, DIAMOND), cardOf(_8, SPADE), cardOf(_2, DIAMOND))},
                new Object[]{true, K, asList(_10, _8, _6), asList(
                        cardOf(K, HEART), cardOf(K, DIAMOND), cardOf(_10, CLUB), cardOf(_8, SPADE), cardOf(_6, DIAMOND), cardOf(_5, SPADE), cardOf(_3, DIAMOND))},
                new Object[]{true, _2, asList(A, K, Q), asList(
                        cardOf(_2, HEART), cardOf(_2, DIAMOND), cardOf(A, CLUB), cardOf(K, SPADE), cardOf(Q, DIAMOND), cardOf(_4, DIAMOND), cardOf(_3, DIAMOND))}

        );
    }

    private boolean hasPair;
    private Rank pairRank;
    private Set<Rank> highCardsRanks;
    private Set<Card> cards;

    public PairEvaluatorTest(boolean hasPair, Rank pairRank, List<Rank> highCardsRanks, List<Card> cards) {
        this.hasPair = hasPair;
        this.pairRank = pairRank;
        if (highCardsRanks != null) {
            this.highCardsRanks = new HashSet<>(highCardsRanks);
            assertEquals(3, this.highCardsRanks.size());
        }

        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() {
        @Nullable Set<Card> hand = twoPairsEvaluator.evaluate(cards);

        if (hasPair) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertEquals(2, hand.stream().filter(card -> card.getRank() == pairRank).count());
            assertTrue(hand.stream().filter(
                    card -> highCardsRanks.stream().filter(
                            rank -> rank == card.getRank()).findFirst().isPresent()
                    ).count() == 3
            );
        } else {
            assertNull(hand);
        }
    }
}