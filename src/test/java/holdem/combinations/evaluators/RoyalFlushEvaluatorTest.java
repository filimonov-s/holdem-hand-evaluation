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
import static holdem.card.Suit.DIAMOND;
import static holdem.card.Suit.HEART;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * @author s.filimonov
 */
@RunWith(Parameterized.class)
public class RoyalFlushEvaluatorTest {

    private RoyalFlushEvaluator royalFlushEvaluator = new RoyalFlushEvaluator();
    private Set<Rank> royalFlushRanks = new HashSet<>(asList(Rank.A, Rank.K, Rank.Q, Rank.J, Rank._10));

    @Parameters(name = "{index}: Royal flush={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{true, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(A, DIAMOND), cardOf(K, DIAMOND), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_10, DIAMOND))},
                new Object[]{true, asList(
                        cardOf(_9, HEART), cardOf(_10, HEART), cardOf(A, DIAMOND), cardOf(K, DIAMOND), cardOf(Q, DIAMOND), cardOf(J, DIAMOND), cardOf(_10, DIAMOND))},
                new Object[]{false, asList(
                        cardOf(A, HEART), cardOf(K, DIAMOND), cardOf(Q, HEART), cardOf(J, HEART), cardOf(_10, HEART), cardOf(_9, HEART), cardOf(_8, HEART))},
                new Object[]{false, asList(
                        cardOf(A, HEART), cardOf(K, HEART), cardOf(Q, HEART), cardOf(J, HEART), cardOf(_9, HEART), cardOf(_8, HEART), cardOf(_7, HEART))}
        );
    }

    private boolean hasRoyalFlush;
    private Set<Card> cards;

    public RoyalFlushEvaluatorTest(boolean hasRoyalFlush, List<Card> cards) {
        this.hasRoyalFlush = hasRoyalFlush;
        this.cards = new HashSet<>(cards);

        assertEquals(7, this.cards.size());
    }

    @Test
    public void evaluate() throws Exception {
        @Nullable Set<Card> hand = royalFlushEvaluator.evaluate(cards);

        if (hasRoyalFlush) {
            assertNotNull(hand);
            assertEquals(5, hand.size());
            assertEquals(hasRoyalFlush, hand.stream()
                    .allMatch(card ->
                            royalFlushRanks.stream().filter(rank -> card.getRank().equals(rank)).findFirst().isPresent()
                    ));
        } else {
            assertNull(hand);
        }
    }


}