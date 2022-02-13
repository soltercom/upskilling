package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class RobotWaitNotify {

    private static final Logger logger = LoggerFactory.getLogger(RobotWaitNotify.class);

    private static final int MAX_REPEATING = 20;
    private static final int STEP_TIME = 200;
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private String currentLeg = RIGHT;

    private final Object monitor = new Object();

    public static void main(String[] args) {
        var robot = new RobotWaitNotify();
        new Thread(() -> robot.move(RIGHT)).start();
        new Thread(() -> robot.move(LEFT)).start();
    }

    private void move(String leg) {
        for (int i = 0; i < MAX_REPEATING; i++) {
            try {
                synchronized (monitor) {
                    while (leg.equals(currentLeg)) {
                        monitor.wait();
                    }

                    logger.info(leg);
                    currentLeg = leg;
                    TimeUnit.MILLISECONDS.sleep(STEP_TIME);
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }


}
