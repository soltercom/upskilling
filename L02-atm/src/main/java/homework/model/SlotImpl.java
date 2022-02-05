package homework.model;

import homework.exception.OutOfMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SlotImpl implements Slot {

    private static final Logger logger = LoggerFactory.getLogger(SlotImpl.class);

    private List<Banknote> banknoteList;

    public SlotImpl() {
        this.banknoteList = new LinkedList<>();
    }

    @Override
    public void add(Banknote banknote) {
        Objects.requireNonNull(banknote);
        logger.info("{} has been added", banknote);
        banknoteList.add(banknote);
    }

    @Override
    public void give(int quantity) {
        if (banknoteList.size() >= quantity) {
            for (var i = 0; i < quantity; i++) {
                var banknote = banknoteList.remove(0);
                logger.info("{} has been given", banknote);
            }
        } else {
            logger.error("Required {}, but remained {}", quantity, banknoteList.size());
            throw new OutOfMoney();
        }
    }

    @Override
    public int quantity() {
        return banknoteList.size();
    }

    @Override
    public SlotState save() {
        var state = new SlotState(banknoteList);
        logger.info("Slot saved.");
        return state;
    }

    @Override
    public void restore(SlotState state) {
        this.banknoteList = new LinkedList<>(state.getBanknoteList());
        logger.info("Slot restored.");
    }
}
