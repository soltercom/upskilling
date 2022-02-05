package model;

import homework.model.ATMImpl;
import homework.model.Banknote;
import homework.model.Nominal;
import homework.model.StorageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ATMStateTest {

    @Test
    @DisplayName("#save() and #restore() shouldn't destroy the ATM object")
    void savepointTest() {
        var atm = new ATMImpl(1L);
        atm.take(List.of(
            new Banknote(1, Nominal.FIFTY),
            new Banknote(2, Nominal.FIVE_HUNDREDS),
            new Banknote(3, Nominal.FIVE_THOUSANDS)
        ));
        atm.offline();
        var expectedOnline = atm.isOnline();
        var expectedSum = atm.getBalance();


        var state = atm.save();
        atm.give(expectedSum);
        atm.restore(state);

        assertThat(atm.getBalance()).isEqualTo(expectedSum);
        assertThat(atm.isOnline()).isEqualTo(expectedOnline);
    }

}
