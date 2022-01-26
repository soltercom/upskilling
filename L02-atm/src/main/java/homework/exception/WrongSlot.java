package homework.exception;

public class WrongSlot extends RuntimeException {

    @Override
    public String getMessage() {
        return "Attempting to put money in wrong slot.";
    }

}
