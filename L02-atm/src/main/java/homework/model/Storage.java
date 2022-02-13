package homework.model;

import java.util.List;

public interface Storage {

    void plus(List<Banknote> banknoteList);

    void give(long sum);

    long getBalance();

    List<Banknote> getBanknoteList();

}
