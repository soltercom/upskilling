import homework.model.ATMImpl;
import homework.service.ATMService;
import homework.service.ATMServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ATMServiceImplTest {

    private ATMService atmService;

    @BeforeEach
    void init() {
        atmService = new ATMServiceImpl(new ATMImpl());
    }

    @Test
    void takeTest() {
        var data = new int[][] {{1000, 20}, {500, 20}, {100, 20}};

        var expectedBalanceBefore = 0L;
        var expectedBalanceAfter = Arrays.stream(data)
                .mapToLong(i -> (long)i[0] * i[1]).sum();

        assertEquals(expectedBalanceBefore, atmService.getBalance());

        for (var slot: data) {
            atmService.take(slot[0], slot[1]);
        }

        assertEquals(expectedBalanceAfter, atmService.getBalance());
    }

    @Test
    void giveTest() {
        var data = new int[][] {{1000, 20}, {500, 20}, {100, 20}};
        var expectedSum = Arrays.stream(data)
                .mapToLong(i -> (long)i[0] * i[1]).sum();

        for (var slot: data) {
            atmService.take(slot[0], slot[1]);
        }

        atmService.give(expectedSum / 2);

        assertEquals(expectedSum / 2, atmService.getBalance());
    }

    @Test
    void balanceTest() {
        var data = new int[][] {{1000, 20}, {500, 20}, {100, 20}};
        var expectedSum = Arrays.stream(data)
                .mapToLong(i -> (long)i[0] * i[1]).sum();

        assertEquals(0L, atmService.getBalance());

        for (var slot: data) {
            atmService.take(slot[0], slot[1]);
        }

        assertEquals(expectedSum, atmService.getBalance());
    }

}
