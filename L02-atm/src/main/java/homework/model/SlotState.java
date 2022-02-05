package homework.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SlotState {

    private final List<Banknote> banknoteList;

    public SlotState(List<Banknote> banknoteList) {
        this.banknoteList = Collections.unmodifiableList(new LinkedList<>(banknoteList));
    }

    public List<Banknote> getBanknoteList() {
        return banknoteList;
    }

}
