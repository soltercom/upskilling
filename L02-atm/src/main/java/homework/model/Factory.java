package homework.model;

public class Factory {

    private Factory() {}

    public static Slot createSlot() {
        return new SlotImpl();
    }

    public static Storage createStorage() {
        return new StorageImpl();
    }

    public static ATM createATM(long id) {
        return new ATMImpl(id);
    }

    public static ATM createATM(long id, ATMState state) {
        return new ATMImpl(id, state);
    }

}
