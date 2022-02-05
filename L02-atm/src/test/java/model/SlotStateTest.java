package model;

import homework.model.Banknote;
import homework.model.Nominal;
import homework.model.SlotImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlotStateTest {

    @Test
    @DisplayName("#save() and #restore() shouldn't destroy the Slot object")
    void savepointTest() {
        var slot = new SlotImpl();
        slot.add(new Banknote(1, Nominal.FIFTY));
        slot.add(new Banknote(2, Nominal.FIFTY));
        slot.add(new Banknote(3, Nominal.FIFTY));

        var state = slot.save();

        slot.give(2);

        slot.restore(state);

        assertThat(slot.quantity()).isEqualTo(3);

        slot.give(3);

        assertThat(slot.quantity()).isZero();
    }

}
