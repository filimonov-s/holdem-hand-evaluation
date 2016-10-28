package holdem.combinations;

import holdem.card.Card;
import holdem.card.Rank;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static holdem.card.Card.cardOf;
import static holdem.card.Rank.*;
import static holdem.card.Suit.*;
import static java.util.stream.Collectors.toSet;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @author s.filimonov
 */
public class CombinationTypeTest {

    @Test
    public void highCardComparator_returnsCorrectValue_whenCombinationsAreNotEqual() {
        Set<Card> withHighAce = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, SPADE), cardOf(_4, CLUB), cardOf(_5, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> withHighKing = Stream
                .of(cardOf(_2, DIAMOND), cardOf(_3, HEART), cardOf(_4, DIAMOND), cardOf(_5, DIAMOND), cardOf(K, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.HIGH_CARD.sameTypeComparator.compare(withHighAce, withHighKing) > 0);
        assertTrue(CombinationType.HIGH_CARD.sameTypeComparator.compare(withHighKing, withHighAce) < 0);
    }

    @Test
    public void highCardComparator_returnsCorrectValue_whenCombinationsAreEqual() {
        Set<Card> withHighAce = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, SPADE), cardOf(_4, CLUB), cardOf(_5, SPADE), cardOf(A, CLUB))
                .collect(toSet());

        assertEquals(0, CombinationType.HIGH_CARD.sameTypeComparator.compare(withHighAce, withHighAce));
    }

    @Test
    public void pairComparator_returnsCorrectValue_whenOnlyHighCardRankIsDifferent() {
        Set<Card> withHighAce = Stream
                .of(cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_3, CLUB), cardOf(_4, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> withHighKing = Stream
                .of(cardOf(_2, DIAMOND), cardOf(_2, HEART), cardOf(_3, DIAMOND), cardOf(_4, DIAMOND), cardOf(K, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.PAIR.sameTypeComparator.compare(withHighAce, withHighKing) > 0);
        assertTrue(CombinationType.PAIR.sameTypeComparator.compare(withHighKing, withHighAce) < 0);
    }

    @Test
    public void pairComparator_returnsCorrectValue_whenPairsAreDifferent() {
        Set<Card> _2s = Stream
                .of(cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_3, CLUB), cardOf(_4, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> _3s = Stream
                .of(cardOf(_3, DIAMOND), cardOf(_3, HEART), cardOf(_4, DIAMOND), cardOf(_5, DIAMOND), cardOf(K, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.PAIR.sameTypeComparator.compare(_3s, _2s) > 0);
        assertTrue(CombinationType.PAIR.sameTypeComparator.compare(_2s, _3s) < 0);
    }

    @Test
    public void twoPairsComparator_returnsCorrectValue_whenHighestPairIsDifferent() {
        Set<Card> _2s_3s_A = Stream
                .of(cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_3, CLUB), cardOf(_3, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> _2s_4s_K = Stream
                .of(cardOf(_2, DIAMOND), cardOf(_2, HEART), cardOf(_4, DIAMOND), cardOf(_4, CLUB), cardOf(K, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.TWO_PAIRS.sameTypeComparator.compare(_2s_4s_K, _2s_3s_A) > 0);
        assertTrue(CombinationType.TWO_PAIRS.sameTypeComparator.compare(_2s_3s_A, _2s_4s_K) < 0);
    }

    @Test
    public void twoPairsComparator_returnsCorrectValue_whenLowestPairIsDifferent() {
        Set<Card> _2s_4s_A = Stream
                .of(cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_4, CLUB), cardOf(_4, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> _3s_4s_K = Stream
                .of(cardOf(_3, DIAMOND), cardOf(_3, HEART), cardOf(_4, DIAMOND), cardOf(_4, CLUB), cardOf(K, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.TWO_PAIRS.sameTypeComparator.compare(_3s_4s_K, _2s_4s_A) > 0);
        assertTrue(CombinationType.TWO_PAIRS.sameTypeComparator.compare(_2s_4s_A, _3s_4s_K) < 0);
    }

    @Test
    public void twoThreeOfAKindComparator_returnsCorrectValue_whenRanksAreDifferent() {
        Set<Card> _2s = Stream
                .of(cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_2, HEART), cardOf(_4, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> _3s = Stream
                .of(cardOf(_3, DIAMOND), cardOf(_3, HEART), cardOf(_3, SPADE), cardOf(_4, CLUB), cardOf(K, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.THREE_OF_A_KIND.sameTypeComparator.compare(_3s, _2s) > 0);
        assertTrue(CombinationType.THREE_OF_A_KIND.sameTypeComparator.compare(_2s, _3s) < 0);
    }

    @Test
    public void twoFourOfAKindComparator_returnsCorrectValue_whenRanksAreDifferent() {
        Set<Card> _2s = Stream
                .of(cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_2, HEART), cardOf(_2, DIAMOND), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> _3s = Stream
                .of(cardOf(_3, DIAMOND), cardOf(_3, HEART), cardOf(_3, DIAMOND), cardOf(_3, CLUB), cardOf(K, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.FOUR_OF_A_KIND.sameTypeComparator.compare(_3s, _2s) > 0);
        assertTrue(CombinationType.FOUR_OF_A_KIND.sameTypeComparator.compare(_2s, _3s) < 0);
    }

    @Test
    public void straightComparator_returnsCorrectValue_whenCombinationContainsAceAndTwoAndRanksAreEqual() {
        Set<Card> straight5 = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, SPADE), cardOf(_4, HEART), cardOf(_5, DIAMOND), cardOf(A, CLUB))
                .collect(toSet());

        assertEquals(0, CombinationType.STRAIGHT.sameTypeComparator.compare(straight5, straight5));
    }

    @Test
    public void straightComparator_returnsCorrectValue_whenCombinationContainsAceAndTwoAndRanksAreNotEqual() {
        Set<Card> straight5 = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, SPADE), cardOf(_4, HEART), cardOf(_5, DIAMOND), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> straight6 = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, SPADE), cardOf(_4, HEART), cardOf(_5, DIAMOND), cardOf(_6, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.STRAIGHT.sameTypeComparator.compare(straight6, straight5) > 0);
        assertTrue(CombinationType.STRAIGHT.sameTypeComparator.compare(straight5, straight6) < 0);
    }

    @Test
    public void straightComparator_returnsCorrectValue_whenCombinationsDoNotContainAceAndTwo() {
        Set<Card> straight6 = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, SPADE), cardOf(_4, HEART), cardOf(_5, DIAMOND), cardOf(_6, CLUB))
                .collect(toSet());
        Set<Card> straight7 = Stream
                .of(cardOf(_3, CLUB), cardOf(_4, SPADE), cardOf(_5, HEART), cardOf(_6, DIAMOND), cardOf(_7, CLUB))
                .collect(toSet());

        assertTrue(CombinationType.STRAIGHT.sameTypeComparator.compare(straight7, straight6) > 0);
        assertTrue(CombinationType.STRAIGHT.sameTypeComparator.compare(straight6, straight7) < 0);
    }

    @Test
    public void flushComparator_returnsCorrectValue_whenNotTheHighestCardsDiffer() {
        Set<Card> A_5_4_3_2 = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, CLUB), cardOf(_4, CLUB), cardOf(_5, CLUB), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> A_6_4_3_2 = Stream
                .of(cardOf(_2, SPADE), cardOf(_3, SPADE), cardOf(_4, SPADE), cardOf(_6, SPADE), cardOf(A, SPADE))
                .collect(toSet());

        assertTrue(CombinationType.FLUSH.sameTypeComparator.compare(A_6_4_3_2, A_5_4_3_2) > 0);
        assertTrue(CombinationType.FLUSH.sameTypeComparator.compare(A_5_4_3_2, A_6_4_3_2) < 0);
    }

    @Test
    public void flushComparator_returnsCorrectValue_whenHighestCardRanksAreDifferent() {
        Set<Card> ace = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, CLUB), cardOf(_4, CLUB), cardOf(_5, CLUB), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> king = Stream
                .of(cardOf(_2, SPADE), cardOf(_3, SPADE), cardOf(_4, SPADE), cardOf(_5, SPADE), cardOf(K, SPADE))
                .collect(toSet());

        assertTrue(CombinationType.FLUSH.sameTypeComparator.compare(ace, king) > 0);
        assertTrue(CombinationType.FLUSH.sameTypeComparator.compare(king, ace) < 0);
    }

    @Test
    public void fullHouseComparator_returnsCorrectValue_whenThreeOfAKindRanksAreDifferent() {
        Set<Card> _2s = Stream
                .of(cardOf(Rank._2, CLUB), cardOf(Rank._2, HEART), cardOf(_2, SPADE), cardOf(A, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> _3s = Stream
                .of(cardOf(_3, SPADE), cardOf(_3, HEART), cardOf(_3, CLUB), cardOf(A, DIAMOND), cardOf(A, HEART))
                .collect(toSet());

        assertTrue(CombinationType.FULL_HOUSE.sameTypeComparator.compare(_3s, _2s) > 0);
        assertTrue(CombinationType.FULL_HOUSE.sameTypeComparator.compare(_2s, _3s) < 0);
    }

    @Test
    public void fullHouseComparator_returnsCorrectValue_whenPairsRanksAreDifferent() {
        Set<Card> aces = Stream
                .of(cardOf(Rank._2, CLUB), cardOf(Rank._2, HEART), cardOf(_2, SPADE), cardOf(A, SPADE), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> kings = Stream
                .of(cardOf(_2, SPADE), cardOf(_2, HEART), cardOf(_2, CLUB), cardOf(K, SPADE), cardOf(K, SPADE))
                .collect(toSet());

        assertTrue(CombinationType.FULL_HOUSE.sameTypeComparator.compare(aces, kings) > 0);
        assertTrue(CombinationType.FULL_HOUSE.sameTypeComparator.compare(kings, aces) < 0);
    }

    @Test
    public void fourOfAKindComparator_returnsCorrectValue_whenFourOfAKindRanksAreDifferent() {
        Set<Card> _2s = Stream
                .of(cardOf(_2, CLUB), cardOf(_2, SPADE), cardOf(_2, HEART), cardOf(_2, DIAMOND), cardOf(A, CLUB))
                .collect(toSet());
        Set<Card> _3s = Stream
                .of(cardOf(_3, CLUB), cardOf(_3, SPADE), cardOf(_3, HEART), cardOf(_3, DIAMOND), cardOf(A, SPADE))
                .collect(toSet());

        assertTrue(CombinationType.FOUR_OF_A_KIND.sameTypeComparator.compare(_3s, _2s) > 0);
        assertTrue(CombinationType.FOUR_OF_A_KIND.sameTypeComparator.compare(_2s, _3s) < 0);
    }

    @Test
    public void straightFlushComparator_returnsCorrectValue_whenHighestCardRanksAreDifferent() {
        Set<Card> _6 = Stream
                .of(cardOf(_2, CLUB), cardOf(_3, CLUB), cardOf(_4, CLUB), cardOf(_5, CLUB), cardOf(Rank._6, CLUB))
                .collect(toSet());
        Set<Card> queen = Stream
                .of(cardOf(_8, SPADE), cardOf(_9, SPADE), cardOf(_10, SPADE), cardOf(J, SPADE), cardOf(Q, SPADE))
                .collect(toSet());

        assertTrue(CombinationType.STRAIGHT_FLUSH.sameTypeComparator.compare(queen, _6) > 0);
        assertTrue(CombinationType.STRAIGHT_FLUSH.sameTypeComparator.compare(_6, queen) < 0);
    }


    @Test(expected = AssertionError.class)
    public void royalFlushComparator_throwsException_always() {
        int compare = CombinationType.ROYAL_FLUSH.sameTypeComparator.compare(Collections.emptySet(), Collections.emptySet());
        assertEquals(0, compare);
    }

}