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
public class HighCardEvaluatorTest {

    private HighCardEvaluator highCardEvaluator = new HighCardEvaluator();

    @Parameters(name = "{index}: Pair={0}")
    public static List<Object[]> data() {
        return asList(

                new Object[]{
                        asList(A, Q, J, _7, _6),
                        asList(cardOf(A, HEART), cardOf(_2, DIAMOND), cardOf(_4, CLUB), cardOf(_6, SPADE), cardOf(_7, DIAMOND), cardOf(Q, SPADE), cardOf(J, DIAMOND))
                },
                new Object[]{asList(A, K, Q, _10, _9),
                        asList(cardOf(A, HEART), cardOf(K, SPADE), cardOf(Q, DIAMOND), cardOf(_10, CLUB), cardOf(_9, DIAMOND), cardOf(_8, SPADE), cardOf(_2, DIAMOND))
                },
                new Object[]{
                        asList(K, Q, _10, _8, _6),
                        asList(cardOf(K, HEART), cardOf(Q, DIAMOND), cardOf(_10, CLUB), cardOf(_8, SPADE), cardOf(_6, DIAMOND), cardOf(_5, SPADE), cardOf(_3, DIAMOND))
                },
                new Object[]{
                        asList(A, K, Q, _5, _4),
                        asList(cardOf(_2, HEART), cardOf(_3, DIAMOND), cardOf(A, CLUB), cardOf(K, SPADE), cardOf(Q, DIAMOND), cardOf(_4, DIAMOND), cardOf(_5, DIAMOND))
                }
        );
    }

    private Set<Rank> highCardsRanks;
    private Set<Card> cards;

    public HighCardEvaluatorTest(List<Rank> highCardsRanks, List<Card> cards) {
        this.highCardsRanks = new HashSet<>(highCardsRanks);
        this.cards = new HashSet<>(cards);

        assertEquals(5, this.highCardsRanks.size());
        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() {
        @Nullable Set<Card> hand = highCardEvaluator.evaluate(cards);

        assertNotNull(hand);
        assertEquals(5, hand.size());
        assertTrue(hand.stream().filter(
                card -> highCardsRanks.stream().filter(
                        rank -> rank == card.getRank()).findFirst().isPresent()
                ).count() == 5
        );
    }
}