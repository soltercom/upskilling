import homework.model.Slot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class SlotTest {

    @Test
    void slotEquality() {

        var slot1 = new Slot(1000, 20);
        var slot2 = new Slot(1000, 1);
        var slot3 = new Slot(100, 20);

        assertEquals(slot1, slot2);

        assertNotEquals(slot1, slot3);

    }
}
