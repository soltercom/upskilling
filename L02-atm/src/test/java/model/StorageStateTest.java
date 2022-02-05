package model;

import homework.model.Banknote;
import homework.model.Nominal;
import homework.model.StorageImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StorageStateTest {

    @Test
    @DisplayName("#save() and #restore() shouldn't destroy the Storage object")
    void savepointTest() {
        var storage = new StorageImpl();
        storage.plus(List.of(
            new Banknote(1, Nominal.FIFTY),
            new Banknote(2, Nominal.FIVE_HUNDREDS),
            new Banknote(3, Nominal.FIVE_THOUSANDS)
        ));
        var expectedSum = storage.getBalance();

        var state = storage.save();
        storage.give(expectedSum);
        storage.restore(state);

        assertThat(storage.getBalance()).isEqualTo(expectedSum);
    }

}
