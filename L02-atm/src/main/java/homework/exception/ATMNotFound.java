package homework.exception;

public class ATMNotFound extends RuntimeException {

    private final long id;

    public ATMNotFound(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Bank with id: " + id + " not found.";
    }

}
