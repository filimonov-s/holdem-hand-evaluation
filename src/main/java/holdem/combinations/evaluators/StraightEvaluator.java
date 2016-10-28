package holdem.combinations.evaluators;

import holdem.card.Card;
import holdem.combinations.CombinationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

import static holdem.card.Rank.A;
import static holdem.card.Rank._2;
import static java.util.Comparator.comparing;

/**
 * @author s.filimonov
 */
public class StraightEvaluator implements HandEvaluator {


    @Override
    public @Nullable Set<Card> evaluate(@NotNull Set<Card> cards) {
        Card[] cardsArray = cards.stream().sorted(comparing(Card::getRank).reversed()).toArray(Card[]::new);
        Card ace = cards.stream().filter(card -> card.getRank() == A).findFirst().orElse(null);

        Set<Card> sequence = new HashSet<>();
        sequence.add(cardsArray[0]);
        for (int i = 1; i < cardsArray.length; i++) {
            if ((cardsArray[i].getRank().ordinal() == cardsArray[i - 1].getRank().ordinal() - 1)) {
                sequence.add(cardsArray[i]);
            } else if ((cardsArray[i].getRank().ordinal() != cardsArray[i - 1].getRank().ordinal())
                    && !(cardsArray[i].getRank() == _2 && ace != null)) {
                sequence.clear();
                sequence.add(cardsArray[i]);
            }

            //noinspection Duplicates
            if (sequence.size() == 5) {
                return sequence;
            } else if (sequence.size() == 4 && cardsArray[i].getRank() == _2 && ace != null) {
                sequence.add(ace);
                return sequence;
            }
        }

        return null;
    }

    @Override
    public @NotNull CombinationType getType() {
        return CombinationType.STRAIGHT;
    }
}
