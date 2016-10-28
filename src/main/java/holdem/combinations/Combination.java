package holdem.combinations;


import org.jetbrains.annotations.NotNull;
import holdem.card.Card;

import java.util.Objects;
import java.util.Set;

/**
 * @author s.filimonov
 */
public final class Combination implements Comparable<Combination> {

    @NotNull
    private final CombinationType type;
    @NotNull
    private final Set<Card> cards;

    public Combination(@NotNull CombinationType type, @NotNull Set<Card> cards) {
        this.type = type;
        this.cards = cards;
    }

    @NotNull
    public CombinationType getType() {
        return type;
    }

    @NotNull
    public Set<Card> getCards() {
        return cards;
    }

    @Override
    public int compareTo(@NotNull Combination o) {
        int compareTypes = type.compareTo(o.getType());
        return compareTypes == 0 ? type.sameTypeComparator.compare(cards, o.getCards()) : compareTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Combination)) return false;
        Combination that = (Combination) o;
        return type == that.type &&
                Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, cards);
    }

    @Override
    public String toString() {
        return "Combination{" +
                "getType=" + type +
                ", cards=" + cards +
                '}';
    }
}
