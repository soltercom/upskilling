package homework.model;

public class OrderNotFoundException extends RuntimeException {

    private final long id;

    public OrderNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Order with id " + id + " not found.";
    }
}
