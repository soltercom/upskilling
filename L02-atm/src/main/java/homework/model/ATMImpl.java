package homework.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ATMImpl implements ATM {

    private static final long LOW_BALANCE_LIMIT = 1000L;

    private static final Logger logger = LoggerFactory.getLogger(ATMImpl.class);

    private final long id;

    private final Storage storage;

    private boolean isOnline;

    private final List<LowBalanceListener> lowBalanceListeners = new ArrayList<>();

    public ATMImpl(long id) {
        logger.info("ATM {} create", id);
        this.id = id;
        this.storage = Factory.createStorage();
        this.isOnline = true;
    }

    public ATMImpl(long id, ATMState state) {
        logger.info("ATM {} restore from state", id);
        this.id = id;
        this.storage = Factory.createStorage();
        this.isOnline = state.isOnline();
        take(state.getBanknoteList());
    }

    @Override
    public void take(List<Banknote> banknoteList) {
        logger.info("ATM {}", id);
        storage.plus(banknoteList);
        if (getBalance() < LOW_BALANCE_LIMIT) {
            lowBalanceListeners.forEach(l -> l.onAction(getId()));
        }
    }

    @Override
    public void give(long sum) {
        storage.give(sum);
        logger.info("ATM {}", id);
        if (getBalance() < LOW_BALANCE_LIMIT) {
            lowBalanceListeners.forEach(l -> l.onAction(getId()));
        }
    }

    @Override
    public long getBalance() {
        return storage.getBalance();
    }

    @Override
    public long getId() {
        return id;
    }

    public boolean isOnline() {
        return isOnline;
    }

    @Override
    public void online() {
        isOnline = true;
        logger.info("ATM {} sets Online", id);
    }

    @Override
    public void offline() {
        isOnline = false;
        logger.info("ATM {} sets Offline", id);
    }

    @Override
    public void addListener(LowBalanceListener listener) {
        lowBalanceListeners.add(listener);
    }

    @Override
    public ATMState save() {
        logger.info("ATM {} savepoint has made", id);
        return new ATMState(storage.getBanknoteList(), isOnline());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ATMImpl)) return false;
        ATMImpl atm = (ATMImpl) o;
        return getId() == atm.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ATM {" +
                "id=" + id +
                ", isOnline=" + isOnline +
                '}';
    }

}
