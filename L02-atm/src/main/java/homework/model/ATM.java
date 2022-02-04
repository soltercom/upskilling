package homework.model;

public interface ATM {

    void take(int nominal, int quantity);

    void give(long sum);

    long getBalance();

}
