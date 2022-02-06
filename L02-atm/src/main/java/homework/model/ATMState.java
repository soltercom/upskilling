package homework.model;

import java.util.List;

public class ATMState {

    private final List<Banknote> banknoteList;

    private final boolean isOnline;

    public ATMState(List<Banknote> banknoteList, boolean isOnline) {
        this.banknoteList = banknoteList;
        this.isOnline = isOnline;
    }

    public List<Banknote> getBanknoteList() {
        return banknoteList;
    }

    public boolean isOnline() {
        return isOnline;
    }

}
