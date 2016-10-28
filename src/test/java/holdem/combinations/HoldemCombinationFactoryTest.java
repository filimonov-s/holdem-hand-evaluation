package holdem.combinations;

import holdem.card.Card;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Set;

import static holdem.card.Card.cardOf;
import static holdem.combinations.CombinationType.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.of;
import static org.junit.Assert.assertEquals;

/**
 * @author s.filimonov
 */
@RunWith(Parameterized.class)
public class HoldemCombinationFactoryTest {

    private HoldemCombinationFactory factory = new HoldemCombinationFactory();

    @Parameterized.Parameters(name = "{index}: Combination={0}")
    public static List<Object[]> data() {
        return asList(
                new Object[]{
                        HIGH_CARD, of(cardOf("2C"), cardOf("3D"), cardOf("4C"), cardOf("6D"), cardOf("8D"), cardOf("9D"), cardOf("JS")
                ).collect(toSet())},
                new Object[]{
                        PAIR, of(cardOf("2C"), cardOf("2D"), cardOf("3C"), cardOf("4D"), cardOf("JD"), cardOf("QD"), cardOf("AS")
                ).collect(toSet())},
                new Object[]{
                        TWO_PAIRS, of(cardOf("2C"), cardOf("2D"), cardOf("3C"), cardOf("3D"), cardOf("JD"), cardOf("QS"), cardOf("AS")
                ).collect(toSet())},
                new Object[]{
                        THREE_OF_A_KIND, of(cardOf("2C"), cardOf("2D"), cardOf("2S"), cardOf("3D"), cardOf("JD"), cardOf("QS"), cardOf("AS")
                ).collect(toSet())}
                ,
                new Object[]{
                        STRAIGHT, of(cardOf("2C"), cardOf("3D"), cardOf("4S"), cardOf("5S"), cardOf("JD"), cardOf("QS"), cardOf("AS")
                ).collect(toSet())},
                new Object[]{
                        FLUSH, of(cardOf("2C"), cardOf("4C"), cardOf("2S"), cardOf("3D"), cardOf("JC"), cardOf("QC"), cardOf("AC")
                ).collect(toSet())},
                new Object[]{
                        FULL_HOUSE, of(cardOf("2C"), cardOf("2D"), cardOf("2S"), cardOf("3D"), cardOf("3C"), cardOf("QS"), cardOf("AS")
                ).collect(toSet())},
                new Object[]{
                        FOUR_OF_A_KIND, of(cardOf("2C"), cardOf("2D"), cardOf("2S"), cardOf("2H"), cardOf("3C"), cardOf("QS"), cardOf("AS")
                ).collect(toSet())},
                new Object[]{
                        STRAIGHT_FLUSH, of(cardOf("2C"), cardOf("3C"), cardOf("4C"), cardOf("5C"), cardOf("6C"), cardOf("QS"), cardOf("AS")
                ).collect(toSet())},
                new Object[]{
                        ROYAL_FLUSH, of(cardOf("2C"), cardOf("3C"), cardOf("10S"), cardOf("JS"), cardOf("QS"), cardOf("KS"), cardOf("AS")
                ).collect(toSet())}
        );
    }

    private CombinationType type;
    private Set<Card> cards;

    public HoldemCombinationFactoryTest(CombinationType type, Set<Card> cads) {
        this.type = type;
        this.cards = cads;

        assertEquals(7, cards.size());
    }

    @Test
    public void buildCombination_returnsCorrespondingCombination_whenInputIsValid() {
        @NotNull Combination combination = factory.buildCombination(cards);

        assertEquals(type, combination.getType());

        System.out.println(combination.getCards());
    }

}