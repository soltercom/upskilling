package homework.model;

import java.util.List;

public interface Slot {

    void add(Banknote banknote);

    void give(int quantity);

    int quantity();

    List<Banknote> getBanknoteList();

}
