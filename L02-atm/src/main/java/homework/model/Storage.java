package homework.model;

import java.util.List;

public interface Storage extends Savepoint<StorageState> {

    void plus(List<Banknote> banknoteList);

    void give(long sum);

    long getBalance();

}
