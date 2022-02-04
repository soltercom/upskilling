package homework.exception;

public class OutOfMoney extends RuntimeException {

    @Override
    public String getMessage() {
        return "Out of money.";
    }

}
