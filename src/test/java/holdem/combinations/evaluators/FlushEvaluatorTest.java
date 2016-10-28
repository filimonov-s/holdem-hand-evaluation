package holdem.combinations.evaluators;

import holdem.card.Card;
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
import static java.util.Comparator.comparing;
import static org.junit.Assert.*;

/**
 * @author s.filimonov
 */
@RunWith(Parameterized.class)
public class FlushEvaluatorTest {

    private FlushEvaluator royalFlushEvaluator = new FlushEvaluator();

    @Parameters(name = "{index}: Flush={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{true, HEART,
                        asList(cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, HEART), cardOf(J, HEART), cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, HEART))},
                new Object[]{true, SPADE,
                        asList(cardOf(_2, SPADE), cardOf(_3, SPADE), cardOf(_4, SPADE), cardOf(J, SPADE), cardOf(Q, SPADE), cardOf(K, SPADE), cardOf(A, SPADE))},
                new Object[]{true, HEART, asList(
                        cardOf(_9, HEART), cardOf(_10, HEART), cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, HEART), cardOf(J, DIAMOND), cardOf(_10, DIAMOND))},
                new Object[]{false, null, asList(
                        cardOf(A, HEART), cardOf(K, DIAMOND), cardOf(Q, HEART), cardOf(J, DIAMOND), cardOf(_10, SPADE), cardOf(_9, CLUB), cardOf(_8, CLUB))},
                new Object[]{false, null, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_9, SPADE), cardOf(_8, SPADE), cardOf(_7, SPADE))}
        );
    }

    private boolean hasFlush;
    private Suit flushSuite;
    private Set<Card> cards;

    public FlushEvaluatorTest(boolean hasFlush, Suit flushSuite, List<Card> cards) {
        this.hasFlush = hasFlush;
        this.cards = new HashSet<>(cards);
        this.flushSuite = flushSuite;

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() throws Exception {
        @Nullable Set<Card> hand = royalFlushEvaluator.evaluate(cards);

        if (hasFlush) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertTrue(hand.stream().allMatch(card -> card.getSuit() == flushSuite));
            assertEquals(
                    cards.stream()
                            .filter(card -> card.getSuit() == flushSuite)
                            .sorted(comparing(Card::getRank).reversed())
                            .limit(5)
                            .mapToInt(card -> card.getRank().ordinal())
                            .sum(),
                    hand.stream().mapToInt(card -> card.getRank().ordinal()).sum()
            );
        } else {
            assertNull(hand);
        }
    }


}