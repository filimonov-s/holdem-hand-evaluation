package holdem.card;

import org.jetbrains.annotations.NotNull;

/**
 * @author s.filimonov
 */
public final class Card {

    @NotNull
    private final Rank rank;
    @NotNull
    private final Suit suit;

    public static Card cardOf(@NotNull String input) {
        boolean rankIs_10 = input.startsWith(Rank._10.getTitle());
        if ((rankIs_10 && input.length() != 3) || (!rankIs_10 && input.length() != 2))
            throw new IllegalArgumentException("Illegal card format.");

        Rank rank = rankIs_10 ? Rank._10 : Rank.fromTitle(String.valueOf(input.charAt(0)));
        if (rank == null)
            throw new IllegalArgumentException("Illegal card format. Rank is not valid.");

        Suit suit = Suit.fromTitle(rankIs_10 ? String.valueOf(input.charAt(2)) : String.valueOf(input.charAt(1)));
        if (suit == null)
            throw new IllegalArgumentException("Illegal card format. Suit is not valid.");

        return Card.cardOf(rank, suit);
    }

    public static Card cardOf(@NotNull Rank rank, @NotNull Suit suit) {
        return new Card(rank, suit);
    }

    private Card(@NotNull Rank rank, @NotNull Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @NotNull
    public Rank getRank() {
        return rank;
    }

    @NotNull
    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return rank == card.rank && suit == card.suit;

    }

    @Override
    public int hashCode() {
        int result = rank.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
}
