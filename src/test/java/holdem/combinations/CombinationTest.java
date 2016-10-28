package holdem.combinations;

import org.junit.Test;

import java.util.stream.Stream;

import static holdem.card.Card.cardOf;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;

/**
 * @author s.filimonov
 */
public class CombinationTest {

    @Test
    public void compareTo() throws Exception {
        Combination combination1 = new Combination(CombinationType.TWO_PAIRS,
                Stream.of(cardOf("2C"), cardOf("3S"), cardOf("AS"), cardOf("AH"), cardOf("3D")).collect(toSet()));

        Combination combination2 = new Combination(CombinationType.TWO_PAIRS,
                Stream.of(cardOf("2D"), cardOf("3S"), cardOf("AS"), cardOf("2S"), cardOf("3D")).collect(toSet()));

        int compareTo = combination1.compareTo(combination2);

        assertTrue(compareTo > 0);
    }

}