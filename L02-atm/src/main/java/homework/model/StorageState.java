package homework.model;

import java.util.Collections;
import java.util.Map;

public class StorageState {

    private final Map<Nominal, SlotState> slots;

    public StorageState(Map<Nominal, SlotState> slots) {
        this.slots = Collections.unmodifiableMap(slots);
    }

    public Map<Nominal, SlotState> getSlots() {
        return slots;
    }
}
