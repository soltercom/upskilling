package homework.model;

public interface ATMGroup {

    void addATM(ATM atm);

    void save();

    void restore();

    void online();

    void offline();

    void getBalance();

    long getBalance(long id);

}
