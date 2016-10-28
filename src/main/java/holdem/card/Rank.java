package holdem.card;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

/**
 * @author s.filimonov
 */
public enum Rank {

    _2("2"),
    _3("3"),
    _4("4"),
    _5("5"),
    _6("6"),
    _7("7"),
    _8("8"),
    _9("9"),
    _10("10"),
    J("J"),
    Q("Q"),
    K("K"),
    A("A");

    @NotNull
    private final String title;

    private static List<Rank> valuesDesc = unmodifiableList(stream(Rank.values()).sorted((o1, o2) -> o2.compareTo(o1)).collect(toList()));
    private static List<Rank> valuesAsc = unmodifiableList(Arrays.asList(values()));

    Rank(@NotNull String title) {
        this.title = title;
    }

    /**
     * @return unmodifiable sorted list.
     */
    public static List<Rank> valuesDesc() {
        return valuesDesc;
    }

    /**
     * @return unmodifiable sorted list.
     */
    public static List<Rank> valuesAsc() {
        return valuesAsc;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    @Nullable
    public static Rank fromTitle(@NotNull String title) {
        return stream(Rank.values()).filter(rank -> rank.getTitle().equals(title)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return title;
    }
}
