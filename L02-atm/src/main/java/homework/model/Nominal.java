package homework.model;

public enum Nominal {

    TEN(10),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDREDS(200),
    FIVE_HUNDREDS(500),
    ONE_THOUSAND(1_000),
    TWO_THOUSANDS(2_000),
    FIVE_THOUSANDS(5_000);

    private final int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
