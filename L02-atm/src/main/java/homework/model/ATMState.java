package homework.model;

public class ATMState {

    private final StorageState storage;

    private final boolean isOnline;

    public ATMState(StorageState storage, boolean isOnline) {
        this.storage = storage;
        this.isOnline = isOnline;
    }

    public StorageState getStorage() {
        return storage;
    }

    public boolean isOnline() {
        return isOnline;
    }

}
