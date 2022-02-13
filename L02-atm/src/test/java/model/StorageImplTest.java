package model;

import homework.exception.NegativeQuantity;
import homework.exception.OutOfMoney;
import homework.model.Banknote;
import homework.model.Nominal;
import homework.model.StorageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StorageImplTest {

    @Test
    @DisplayName("#plus() should increase the sum")
    void plusTest() {
        var storage = new StorageImpl();
        var expectedSum = 0L;
        var banknoteList = new ArrayList<Banknote>();
        var serial = 0;

        for (var nominal: Nominal.values()) {
            banknoteList.add(new Banknote(++serial, nominal));
            expectedSum += nominal.getValue();
        }

        storage.plus(banknoteList);

        assertThat(storage.getBalance()).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("#plus() double invoke should increase the sum in twice")
    void doublePlusTest() {
        var storage = new StorageImpl();
        var expectedSum = 0L;
        var banknoteList = new ArrayList<Banknote>();
        var serial = 0;

        for (var nominal: Nominal.values()) {
            banknoteList.add(new Banknote(++serial, nominal));
            expectedSum += nominal.getValue();
        }

        storage.plus(banknoteList);
        storage.plus(banknoteList);

        assertThat(storage.getBalance()).isEqualTo(2 * expectedSum);
    }

    @Test
    @DisplayName("#give() too little sum should throw OutOfMoney exception")
    void giveStrangeSumTest() {
        var storage = new StorageImpl();
        storage.plus(List.of(new Banknote(1, Nominal.FIFTY)));

        assertThatExceptionOfType(OutOfMoney.class)
            .isThrownBy(() -> storage.give(2));
    }

    @Test
    @DisplayName("#give() from empty Storage should throw OutOfMoney exception")
    void giveFromEmptyStorageTest() {
        var storage = new StorageImpl();

        assertThatExceptionOfType(OutOfMoney.class)
                .isThrownBy(() -> storage.give(Nominal.FIFTY.getValue()));
    }

    @Test
    @DisplayName("#give() negative sum should throw OutOfMoney exception")
    void giveNegativeSumTest() {
        var storage = new StorageImpl();

        assertThatExceptionOfType(NegativeQuantity.class)
                .isThrownBy(() -> storage.give(-10L));
    }

    @Test
    @DisplayName("#give() impossible combination of banknotes should throw OutOfMoney exception")
    void giveImpossibleCombinationOfSum() {

        var storage = new StorageImpl();
        storage.plus(List.of(
            new Banknote(1, Nominal.FIVE_THOUSANDS),
            new Banknote(2, Nominal.TWO_HUNDREDS),
            new Banknote(2, Nominal.ONE_THOUSAND)
        ));

        assertThatExceptionOfType(OutOfMoney.class)
                .isThrownBy(() -> storage.give(Nominal.FIVE_HUNDREDS.getValue()));
    }

    @Test
    @DisplayName("#give() should decrease the sum")
    void giveTest() {
        var rnd = new Random();
        var expectedSum = 0L;
        var storage = new StorageImpl();
        var banknoteList = new ArrayList<Banknote>(20);

        for (var i = 1; i < 21; i++) {
            var nominal = Nominal.values()[rnd.nextInt(Nominal.values().length)];
            banknoteList.add(new Banknote(i, nominal));
            expectedSum += nominal.getValue();
        }
        storage.plus(banknoteList);
        storage.give(expectedSum);

        assertThat(storage.getBalance())
                .isZero();
    }

    @Test
    @DisplayName("#getBalance() should return the correct sum")
    void getBalanceTest() {
        var rnd = new Random();
        var expectedSum = 0L;
        var storage = new StorageImpl();
        var banknoteList = new ArrayList<Banknote>(20);

        for (var i = 1; i < 21; i++) {
            var nominal = Nominal.values()[rnd.nextInt(Nominal.values().length)];
            banknoteList.add(new Banknote(i, nominal));
            expectedSum += nominal.getValue();
        }
        storage.plus(banknoteList);

        assertThat(storage.getBalance())
            .isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("#getBanknoteList() should return correct List<Banknote>")
    void getBanknoteListTest() {
        var storage = new StorageImpl();
        var banknoteList = List.of(
            new Banknote(1, Nominal.FIFTY),
            new Banknote(2, Nominal.FIVE_HUNDREDS),
            new Banknote(3, Nominal.FIVE_THOUSANDS));
        storage.plus(banknoteList);

        assertThat(storage.getBanknoteList())
                .hasSize(3)
                .containsAll(banknoteList);
    }

}
