package model;

import homework.exception.OutOfMoney;
import homework.model.Banknote;
import homework.model.Nominal;
import homework.model.SlotImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SlotTest {

    @Test
    @DisplayName("#add() null should throw exception")
    void addNullBanknoteThrowsExceptionTest() {
        var slot = new SlotImpl();

        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> slot.add(null));
    }

    @Test
    @DisplayName("#give() quantity more than size of the Slot should throw exception")
    void giveAGreatDealOfMoneyExceptionTest() {
        var slot = new SlotImpl();

        slot.add(new Banknote(1, Nominal.FIFTY));

        assertThatExceptionOfType(OutOfMoney.class)
                .isThrownBy(() -> slot.give(2));
    }

    @Test
    @DisplayName("#add() should increase the size of the Slot")
    void addBanknoteTest() {
        var slot = new SlotImpl();
        var banknote = new Banknote(1, Nominal.FIFTY);

        slot.add(banknote);

        assertThat(slot.quantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("#give() should decrease the size of the Slot")
    void giveBanknoteTest() {
        var slot = new SlotImpl();
        var banknote = new Banknote(1, Nominal.FIFTY);

        slot.add(banknote);
        slot.give(1);

        assertThat(slot.quantity()).isZero();
    }

}
