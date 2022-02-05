package homework.model;

import java.util.List;
import java.util.Objects;

public class Banknote {

    private final int id;
    private final Nominal nominal;

    public Banknote(int id, Nominal nominal) {
        this.id = id;
        this.nominal = nominal;
    }

    public static long getSum(List<Banknote> banknoteList) {
        return banknoteList.stream()
            .filter(Objects::nonNull)
            .map(Banknote::getNominal)
            .mapToLong(Nominal::getValue).sum();
    }

    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Banknote)) return false;
        Banknote banknote = (Banknote) o;
        return id == banknote.id && getNominal() == banknote.getNominal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getNominal());
    }

    @Override
    public String toString() {
        return "Banknote{" +
                "serial=" + id +
                ", nominal=" + nominal +
                '}';
    }
}
