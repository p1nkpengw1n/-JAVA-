package hu.antalnagy.connect4;

public enum TableSize {
    SMALL,
    MEDIUM,
    LARGE;

    public String[] toStringImpl() {
        return new String[]{"SMALL_8x5", "MEDIUM_10x6", "LARGE_12x7"};
    }
}
