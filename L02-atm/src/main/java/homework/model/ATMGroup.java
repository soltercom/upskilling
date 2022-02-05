package homework.model;

public interface ATMGroup {

    void addATM(long id);

    void save();

    void restore();

    void online();

    void offline();

    void getBalance();

    long getBalance(long id);

}
