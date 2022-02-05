package homework.exception;

public class InvalidBanknote extends RuntimeException {

    @Override
    public String getMessage() {
        return "Invalid banknote.";
    }

}
