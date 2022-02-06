package model;

import homework.exception.NegativeQuantity;
import homework.exception.OutOfMoney;
import homework.model.ATMImpl;
import homework.model.Banknote;
import homework.model.Nominal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ATMImplTest {

    @Test
    @DisplayName("#save() should save current state in the ATMState")
    void saveTest() {
        var atm = new ATMImpl(1L);
        atm.take(List.of(
            new Banknote(1, Nominal.FIFTY),
            new Banknote(2, Nominal.FIVE_HUNDREDS),
            new Banknote(3, Nominal.FIVE_THOUSANDS)
        ));
        atm.offline();

        var state = atm.save();

        assertThat(state.isOnline()).isFalse();
        assertThat(state.getBanknoteList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("#save() and #restore() should restore the state of the ATM")
    void saveAndRestoreTest() {
        var atm = new ATMImpl(1L);
        atm.take(List.of(
            new Banknote(1, Nominal.FIFTY),
            new Banknote(2, Nominal.FIVE_HUNDREDS),
            new Banknote(3, Nominal.FIVE_THOUSANDS)
        ));
        var expectedSum = atm.getBalance();
        atm.offline();

        var state = atm.save();
        atm.give(5_500);
        atm.online();
        atm = new ATMImpl(1L, state);

        assertThat(atm).hasFieldOrPropertyWithValue("isOnline", false);
        assertThat(atm.getBalance()).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("#take() should increase balance correctly")
    void takeTest() {
        var atm = new ATMImpl(1L);
        var banknoteList = List.of(
            new Banknote(1, Nominal.TEN),
            new Banknote(2, Nominal.FIFTY),
            new Banknote(3, Nominal.FIFTY),
            new Banknote(4, Nominal.TEN),
            new Banknote(5, Nominal.ONE_HUNDRED));

        assertThat(atm.getBalance()).isZero();

        atm.take(banknoteList.subList(0, 2));
        assertThat(atm.getBalance()).isEqualTo(60L);

        atm.take(banknoteList.subList(2, 5));
        assertThat(atm.getBalance()).isEqualTo(220L);
    }

    @Test
    @DisplayName("#take() null banknote list should throw NullPointerException")
    void takeNullListTest() {
        var atm = new ATMImpl(1L);

        assertThatThrownBy(() -> atm.take(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("#give() negative sum should throw ")
    void giveNegativeTest() {
        var atm = new ATMImpl(1L);

        assertThatThrownBy(() -> atm.give(-1L))
                .isInstanceOf(NegativeQuantity.class);
    }

    @Test
    @DisplayName("#give() should decrease balance correctly")
    void giveTest() {
        var atm = new ATMImpl(1L);
        atm.take(List.of(
            new Banknote(1, Nominal.FIVE_HUNDREDS),
            new Banknote(2, Nominal.ONE_HUNDRED),
            new Banknote(3, Nominal.FIVE_HUNDREDS)
        ));
        var expectedBalance = atm.getBalance() - 500L;

        atm.give(500L);

        assertThat(atm.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    @DisplayName("#give() should return all money without errors")
    void giveAllMoneyTest() {
        var atm = new ATMImpl(1L);
        atm.take(List.of(
                new Banknote(1, Nominal.FIVE_THOUSANDS),
                new Banknote(2, Nominal.ONE_HUNDRED),
                new Banknote(3, Nominal.FIVE_THOUSANDS)
        ));

        atm.give(atm.getBalance());

        assertThat(atm.getBalance()).isZero();
    }

    @Test
    @DisplayName("#give() the attempt to give small amount of money should return throw OutOfMoney exception")
    void giveSmallAmountOfMoneyTest() {
        var atm = new ATMImpl(1L);
        atm.take(List.of(
                new Banknote(1, Nominal.FIVE_THOUSANDS),
                new Banknote(2, Nominal.ONE_HUNDRED),
                new Banknote(3, Nominal.FIVE_THOUSANDS)
        ));

        assertThatThrownBy(() -> atm.give(1L))
                .isInstanceOf(OutOfMoney.class);
    }

    @Test
    @DisplayName("#give() the attempt to give money more than atm has should return throw OutOfMoney exception")
    void giveLargeAmountOfMoneyTest() {
        var atm = new ATMImpl(1L);
        atm.take(List.of(
                new Banknote(1, Nominal.FIVE_THOUSANDS),
                new Banknote(2, Nominal.ONE_HUNDRED),
                new Banknote(3, Nominal.FIVE_THOUSANDS)
        ));

        var amount = atm.getBalance() + 1L;

        assertThatThrownBy(() -> atm.give(amount))
                .isInstanceOf(OutOfMoney.class);
    }

}
