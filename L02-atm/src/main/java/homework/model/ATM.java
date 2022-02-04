package homework.model;

import homework.exception.NegativeNominal;
import homework.exception.NegativeQuantity;
import homework.exception.OutOfMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ATM {

    private static final Logger logger = LoggerFactory.getLogger(ATM.class);

    private final SortedSet<Slot> slots;

    public ATM() {
        slots = new TreeSet<>(Comparator.comparingInt(Slot::getNominal).reversed());
    }

    public void take(int nominal, int quantity) {
        if (nominal <= 0) {
            logger.error("Negative nominal {} entered", nominal);
            throw new NegativeNominal();
        }
        if (quantity <= 0) {
            logger.error("Negative quantity {} entered", quantity);
            throw new NegativeQuantity();
        }
        var q = slots.stream()
            .filter(s -> s.getNominal() == nominal)
            .mapToInt(Slot::getQuantity).sum();
        var slot = new Slot(nominal, q + quantity);
        slots.remove(slot);
        slots.add(slot);
        logger.info("Slot {} quantity has changed from {} to {}", nominal, q, q + quantity);
    }

    private HashMap<Slot, Integer> prepareGive(long sum) {
        var remainingSum = sum;
        var writeOffMap = new HashMap<Slot, Integer>();

        for (var slot: slots) {
            if (remainingSum == 0) {
                break;
            }

            if (slot.getNominal() <= remainingSum && slot.getQuantity() > 0) {
                var q = (int) Math.min(slot.getQuantity(), remainingSum / slot.getNominal());
                writeOffMap.put(slot, q);
                remainingSum -= (long) q * slot.getNominal();
            }
        }

        if (remainingSum > 0) {
            throw new OutOfMoney();
        }

        return writeOffMap;
    }

    private void doGive(HashMap<Slot, Integer> map) {
        for (var entry: map.entrySet()) {
            var slot = entry.getKey();
            var qBefore = slot.getQuantity();
            var q = slot.getQuantity() - entry.getValue();

            slots.remove(slot);
            if (q > 0) {
                slots.add(new Slot(slot.getNominal(), q));
                logger.info("Slot {} quantity has changed from {} to {}", slot.getNominal(), qBefore, q);
            } else {
                logger.info("Slot {} is removed", slot.getNominal());
            }
        }
    }

    public void give(long sum) {
        if (sum <= 0) {
            logger.error("Negative sum {} entered", sum);
            throw new NegativeQuantity();
        }

        var map = prepareGive(sum);
        doGive(map);
    }

    public long getBalance() {
        return slots.stream()
            .mapToLong(Slot::getBalance)
            .sum();
    }

    // for test purpose only
    public SortedSet<Slot> getSlots() {
        return Collections.unmodifiableSortedSet(slots);
    }

}
