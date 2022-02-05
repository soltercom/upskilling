package homework.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ATMImpl implements ATM {

    private static final long LOW_BALANCE_LIMIT = 1000L;

    private static final Logger logger = LoggerFactory.getLogger(ATMImpl.class);

    private final long id;

    private Storage storage;

    private boolean isOnline;

    private final List<LowBalanceListener> lowBalanceListeners;

    public ATMImpl(long id) {
        this.id = id;
        this.storage = new StorageImpl();
        this.isOnline = true;
        this.lowBalanceListeners = new ArrayList<>();
    }

    @Override
    public void take(List<Banknote> banknoteList) {
        storage.plus(banknoteList);
        if (getBalance() < LOW_BALANCE_LIMIT) {
            lowBalanceListeners.forEach(l -> l.onAction(getId()));
        }
    }

    @Override
    public void give(long sum) {
        storage.give(sum);
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
    }

    @Override
    public void offline() {
        isOnline = false;
    }

    @Override
    public void addListener(LowBalanceListener listener) {
        lowBalanceListeners.add(listener);
    }

    @Override
    public ATMState save() {
        return new ATMState(storage.save(), isOnline);
    }

    @Override
    public void restore(ATMState state) {
        this.storage = new StorageImpl();
        this.storage.restore(state.getStorage());
        this.isOnline = state.isOnline();
    }

    @Override
    public String toString() {
        return "ATM {" +
                "id=" + id +
                ", isOnline=" + isOnline +
                '}';
    }
}
