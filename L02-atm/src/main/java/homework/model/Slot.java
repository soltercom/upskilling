package homework.model;

public interface Slot extends Savepoint<SlotState> {

    void add(Banknote banknote);

    void give(int quantity);

    int quantity();

}
