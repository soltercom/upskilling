package homework.service;

import homework.exception.OutOfMoney;

public interface ATMService {

    void take(int nominal, int quantity);

    void give(long sum) throws OutOfMoney;

    long getBalance();

}
