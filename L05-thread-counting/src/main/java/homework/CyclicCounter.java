package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class CyclicCounter {

    private static final Logger logger = LoggerFactory.getLogger(CyclicCounter.class);

    private static final int MAX_REPEATING = 20;
    private static final int STEP_TIME = 200;
    private static final int MAX_COUNTER = 5;
    private static final int MIN = 1;
    private static final int MAX = 10;

    private static int currentIndex = 0;
    private static int counter = 0;
    private static int sign = 1;

    public static void main(String[] args) {
        var counter = new CyclicCounter();

        for (int i = 0; i < MAX_COUNTER; i++) {
            final var id = i;
            var t = new Thread(() -> counter.count(id));
            t.setName("Thread " + (id + 1));
            t.start();
        }
    }

    private synchronized void next() {
        counter += sign;
        if ((counter == MIN && sign < 0) || (counter == MAX)) {
            sign = -sign;
        }
    }

    private synchronized void count(int index) {
        for (int i = 0; i < MAX_REPEATING; i++) {
            try {
                while (index != currentIndex) {
                    wait();
                }

                if (index == 0) {
                    next();
                }

                logger.info("{}", counter);
                currentIndex = (index + 1) % MAX_COUNTER;
                TimeUnit.MILLISECONDS.sleep(STEP_TIME);
                notifyAll();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }
    }


}
