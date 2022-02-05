package homework.model;

import java.util.List;

public interface ATM extends Savepoint<ATMState> {

    void take(List<Banknote> banknoteList);

    void give(long sum);

    long getBalance();

    long getId();

    void online();

    void offline();

    void addListener(LowBalanceListener listener);

}
