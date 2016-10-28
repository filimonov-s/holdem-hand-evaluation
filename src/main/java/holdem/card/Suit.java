package holdem.card;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Arrays.stream;

/**
 * @author s.filimonov
 */
public enum Suit {
    HEART("H"),
    DIAMOND("D"),
    CLUB("C"),
    SPADE("S");

    @NotNull
    private final String title;

    Suit(@NotNull String title) {
        this.title = title;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @Nullable
    public static Suit fromTitle(String s) {
        return stream(values()).filter(suit -> s.equals(suit.getTitle())).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return title;
    }
}
