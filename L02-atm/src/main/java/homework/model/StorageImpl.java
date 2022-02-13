package homework.model;

import homework.exception.InvalidBanknote;
import homework.exception.NegativeQuantity;
import homework.exception.OutOfMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class StorageImpl implements Storage {

    private static final Logger logger = LoggerFactory.getLogger(StorageImpl.class);

    private static final Comparator<Nominal> comparator = Comparator.comparingInt(Nominal::getValue).reversed();

    private final SortedMap<Nominal, Slot> slots;

    public StorageImpl() {
        slots = new TreeMap<>(comparator);

        for (var nominal: Nominal.values()) {
            slots.put(nominal, Factory.createSlot());
        }
    }

    @Override
    public void plus(List<Banknote> banknoteList) {
        Objects.requireNonNull(banknoteList);

        for (var banknote: banknoteList) {
            plus(banknote);
        }
    }

    @Override
    public void give(long sum) {
        if (sum <= 0) {
            logger.error("Negative sum {} entered", sum);
            throw new NegativeQuantity();
        }

        minus(prepareGive(sum));
    }

    @Override
    public long getBalance() {
        return slots.entrySet().stream()
            .mapToLong(entry -> (long) entry.getKey().getValue() * entry.getValue().quantity())
            .sum();
    }

    @Override
    public List<Banknote> getBanknoteList() {
        return slots.values().stream()
                .flatMap(slot -> slot.getBanknoteList().stream())
                .toList();
    }

    private void plus(Banknote banknote) {
        Objects.requireNonNull(banknote);

        var nominal = banknote.getNominal();

        if (slots.containsKey(nominal)) {
            slots.get(nominal).add(banknote);
        } else {
            logger.error("Invalid banknote {}", banknote);
            throw new InvalidBanknote();
        }
    }

    private void minus(Nominal nominal, int quantity) {
        Objects.requireNonNull(nominal, "NULL pointer Nominal.");

        slots.get(nominal).give(quantity);
    }

    private void minus(Map<Nominal, Integer> map) {
        for (var entry: map.entrySet()) {
            minus(entry.getKey(), entry.getValue());
        }
    }

    private EnumMap<Nominal, Integer> prepareGive(long sum) {
        var remainingSum = sum;
        var writeOffMap = new EnumMap<Nominal, Integer>(Nominal.class);

        for (var entry: slots.entrySet()) {
            var nominal = entry.getKey();
            var quantityInSlot = entry.getValue().quantity();
            if (quantityInSlot > 0 && quantityInSlot <= remainingSum) {
                var q = (int) Math.min(quantityInSlot, remainingSum / nominal.getValue());
                writeOffMap.put(entry.getKey(), q);
                remainingSum -= (long) q * nominal.getValue();
            }
        }

        if (remainingSum > 0) {
            throw new OutOfMoney();
        }

        return writeOffMap;
    }

}
