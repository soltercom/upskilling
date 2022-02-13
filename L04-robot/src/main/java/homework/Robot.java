package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Robot {

    private static final Logger logger = LoggerFactory.getLogger(Robot.class);

    private static final AtomicBoolean TURN = new AtomicBoolean(true);

    private static final int MAX_REPEATING = 20;
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final int STEP_TIME = 200;

    public static void main(String[] args) {
        new Thread(new RightMove()).start();
        new Thread(new LeftMove()).start();
    }

    private static class LeftMove implements Runnable {
        @Override
        public void run() {
            for (var i = 0; i < MAX_REPEATING; i++) {
                while (!TURN.get()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(STEP_TIME);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }
                logger.info(LEFT);
                TURN.set(false);
            }
        }
    }

    private static class RightMove implements Runnable {
        @Override
        public void run() {
            for (var i = 0; i < MAX_REPEATING; i++) {
                while (TURN.get()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(STEP_TIME);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }
                logger.info(RIGHT);
                TURN.set(true);
            }
        }
    }

}
