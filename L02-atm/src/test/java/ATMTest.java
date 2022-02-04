import homework.exception.NegativeNominal;
import homework.exception.NegativeQuantity;
import homework.exception.OutOfMoney;
import homework.model.ATMImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ATMTest {

    private ATMImpl atm;

    @BeforeEach
    void init() {
        atm = new ATMImpl();
    }

    private void chargeATM() {
        var data = new int[][] {{100, 20}, {500, 20}, {1000, 20}};

        for (var slot: data) {
            atm.take(slot[0], slot[1]);
        }
    }

    @Test
    void correctOrderTest() {

        var rnd = new Random();
        var MAX = 10;

        var arr = new int[MAX];
        for (var i = 0; i < MAX; i++) {
            arr[i] = rnd.nextInt(1000);
            atm.take(arr[i], 1);
        }

        Arrays.sort(arr);
        var i = 9;
        for (var slot: atm.getSlots()) {
            assertEquals(arr[i--], slot.getNominal());
        }

    }

    @Test
    void takeTest() {
         var expectedSize = 3;

         atm.take(100, 20);
         atm.take(500, 20);
         atm.take(1000, 20);

         assertEquals(100 * 20 + 500 * 20 + 1000 * 20, atm.getBalance());
         assertEquals(expectedSize, atm.getSlots().size());

         atm.take(100, 3);

         assertEquals(100 * 23 + 500 * 20 + 1000 * 20, atm.getBalance());

         atm.take(50, 5);

        assertEquals(50 * 5 + 100 * 23 + 500 * 20 + 1000 * 20, atm.getBalance());
    }

    @Test
    void takeNegativeNominalTest() {
        assertThrows(NegativeNominal.class, () -> atm.take(-1, 10));
    }

    @Test
    void takeNegativeQuantityTest() {
        assertThrows(NegativeQuantity.class, () -> atm.take(10, -1));
    }

    @Test
    void giveNegativeTest() {
        assertThrows(NegativeQuantity.class, () -> atm.give(-1L));
    }

    @Test
    void giveTest() {
        chargeATM();
        var expectedBalance = atm.getBalance() - 500L;

        atm.give(500L);

        assertEquals(expectedBalance, atm.getBalance());
    }

    @Test
    void giveAllMoneyTest() {
        chargeATM();

        atm.give(atm.getBalance());

        assertEquals(0L, atm.getBalance());
    }

    @Test
    void giveSmallAmountOfMoneyTest() {
        chargeATM();

        assertThrows(OutOfMoney.class, () -> atm.give(1L));
    }

    @Test
    void giveLargeAmountOfMoneyTest() {
        chargeATM();

        var amount = atm.getBalance() + 1L;

        assertThrows(OutOfMoney.class, () -> atm.give(amount));
    }

}
