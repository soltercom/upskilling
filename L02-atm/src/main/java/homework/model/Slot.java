package homework.model;

import java.util.Objects;

public class Slot {

    private final int nominal;
    private final int quantity;

    public Slot(int nominal, int quantity) {
        this.nominal = nominal;
        this.quantity = quantity;
    }

    public int getNominal() {
        return nominal;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getBalance() {
        return (long) nominal * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slot slot)) return false;
        return getNominal() == slot.getNominal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNominal());
    }

    @Override
    public String toString() {
        return "{" + nominal + "$ = " + quantity + '}';
    }
}
