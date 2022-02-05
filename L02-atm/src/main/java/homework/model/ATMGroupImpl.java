package homework.model;

import homework.exception.ATMNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ATMGroupImpl implements ATMGroup, LowBalanceListener {

    private static final Logger logger = LoggerFactory.getLogger(ATMGroupImpl.class);

    private final Map<Long, ATMState> savePoints;

    private final Map<Long, ATM> group;

    public ATMGroupImpl() {
        this.group = new HashMap<>();
        this.savePoints = new HashMap<>();
    }

    public void addATM(long id) {
        group.put(id, new ATMImpl(id));
    }

    @Override
    public void save() {
        for (var entry: group.entrySet()) {
            savePoints.put(entry.getKey(), entry.getValue().save());
        }
    }

    @Override
    public void restore() {
        for (var entry: savePoints.entrySet()) {
            var atm = new ATMImpl(entry.getKey());
            atm.restore(entry.getValue());
        }
    }

    @Override
    public void online() {
        for (var atm: group.values()) {
            atm.online();
        }
    }

    @Override
    public void offline() {
        for (var atm: group.values()) {
            atm.offline();
        }
    }

    @Override
    public void getBalance() {
        for (var atm: group.values()) {
            logger.info("{} balance = {}", atm, getBalance(atm.getId()));
        }
    }

    @Override
    public long getBalance(long id) {
        return group.values().stream()
            .filter(atm -> atm.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ATMNotFound(id))
            .getBalance();
    }


    @Override
    public void onAction(long id) {
        var balance = getBalance(id);
        logger.info("ATM {} has low balance: {}", id, balance);
    }
}
