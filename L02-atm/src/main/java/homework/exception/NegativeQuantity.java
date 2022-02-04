package homework.exception;

public class NegativeQuantity extends RuntimeException {

    @Override
    public String getMessage() {
        return "Quantity should be always positive.";
    }

}
