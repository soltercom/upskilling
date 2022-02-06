package model;

import homework.model.ATMGroupImpl;
import homework.model.Banknote;
import homework.model.Factory;
import homework.model.Nominal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ATMGroupImplTest {

    @Test
    @DisplayName("#getBalance(long) should return correct balance")
    void getBalanceTest() {
        var atmA = Factory.createATM(1L);
        var atmB = Factory.createATM(2L);
        var group = new ATMGroupImpl();
        group.addATM(atmA);
        group.addATM(atmB);
        atmA.take(List.of(
            new Banknote(1, Nominal.FIVE_THOUSANDS),
            new Banknote(2, Nominal.FIVE_HUNDREDS),
            new Banknote(3, Nominal.FIFTY)
        ));
        atmB.take(List.of(
                new Banknote(1, Nominal.ONE_THOUSAND),
                new Banknote(2, Nominal.ONE_HUNDRED),
                new Banknote(3, Nominal.TEN)
        ));

        assertThat(group.getBalance(1L))
            .isEqualTo(5_550L);

        assertThat(group.getBalance(2L))
                .isEqualTo(1_110L);
    }

    @Test
    @DisplayName("#save() & #restore() should make state copy of the atms and restore from copy")
    void saveRestoreTest() {
        var atmA = Factory.createATM(1L);
        var atmB = Factory.createATM(2L);
        var group = new ATMGroupImpl();
        group.addATM(atmA);
        group.addATM(atmB);
        atmA.take(List.of(
                new Banknote(1, Nominal.FIVE_THOUSANDS),
                new Banknote(2, Nominal.FIVE_HUNDREDS),
                new Banknote(3, Nominal.FIFTY)
        ));
        atmB.take(List.of(
                new Banknote(1, Nominal.ONE_THOUSAND),
                new Banknote(2, Nominal.ONE_HUNDRED),
                new Banknote(3, Nominal.TEN)
        ));

        group.save();
        atmA.give(5_500L);
        atmB.give(10L);
        group.restore();

        assertThat(group.getBalance(1L)).isEqualTo(5_550L);
        assertThat(group.getBalance(2L)).isEqualTo(1_110L);
    }

    @Test
    @DisplayName("#online() should set Online state for all atms")
    void onlineTest() {
        var atmA = Factory.createATM(1L);
        atmA.offline();
        var atmB = Factory.createATM(2L);
        var atmC = Factory.createATM(3L);
        atmC.offline();
        var group = new ATMGroupImpl();
        group.addATM(atmA);
        group.addATM(atmB);
        group.addATM(atmC);

        group.online();

        assertThat(atmA)
            .hasFieldOrPropertyWithValue("isOnline", true);
        assertThat(atmB)
            .hasFieldOrPropertyWithValue("isOnline", true);
        assertThat(atmC)
            .hasFieldOrPropertyWithValue("isOnline", true);
    }

    @Test
    @DisplayName("#offline() should set Offline state for all atms")
    void offlineTest() {
        var atmA = Factory.createATM(1L);
        var atmB = Factory.createATM(2L);
        var atmC = Factory.createATM(3L);
        var group = new ATMGroupImpl();
        group.addATM(atmA);
        group.addATM(atmB);
        group.addATM(atmC);

        group.offline();

        assertThat(atmA)
                .hasFieldOrPropertyWithValue("isOnline", false);
        assertThat(atmB)
                .hasFieldOrPropertyWithValue("isOnline", false);
        assertThat(atmC)
                .hasFieldOrPropertyWithValue("isOnline", false);
    }

    @Test
    @DisplayName("#getBalance() should inform about balances of all atms")
    void getAllBalanceTest() {
        var atmA = Factory.createATM(1L);
        var atmB = Factory.createATM(2L);
        var atmC = Factory.createATM(3L);
        var group = new ATMGroupImpl();
        group.addATM(atmA);
        group.addATM(atmB);
        group.addATM(atmC);
        atmA.take(List.of(
            new Banknote(1, Nominal.FIVE_THOUSANDS),
            new Banknote(2, Nominal.FIVE_HUNDREDS),
            new Banknote(3, Nominal.FIFTY)
        ));
        atmB.take(List.of(
            new Banknote(1, Nominal.ONE_THOUSAND),
            new Banknote(2, Nominal.ONE_HUNDRED),
            new Banknote(3, Nominal.TEN)
        ));

       group.getBalance();
    }

    @Test
    @DisplayName("#LowBalanceListener listener should get message when atm balance drops")
    void lowBalanceListenerTest() {
        var atmA = Factory.createATM(1L);
        var atmB = Factory.createATM(2L);
        var group = new ATMGroupImpl();
        group.addATM(atmA);
        group.addATM(atmB);
        atmA.take(List.of(
                new Banknote(1, Nominal.FIVE_THOUSANDS),
                new Banknote(2, Nominal.FIVE_HUNDREDS),
                new Banknote(3, Nominal.FIFTY)
        ));
        atmB.take(List.of(
                new Banknote(1, Nominal.ONE_THOUSAND),
                new Banknote(2, Nominal.ONE_HUNDRED),
                new Banknote(3, Nominal.TEN)
        ));

        atmA.give(5000L);
    }

}
