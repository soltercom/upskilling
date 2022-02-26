package homework.utils;

public class IdGenerator {

    private static long orderId = 0;
    private static long orderItemId = 0;

    private IdGenerator(){}

    public static long getNextOrderId() {
        return ++orderId;
    }

    public static long getCurrentOrderId() { return orderId; }

    public static long getNextOrderItemId() {
        return ++orderItemId;
    }

    public static long getCurrentOrderItemId() { return orderItemId; }

}
