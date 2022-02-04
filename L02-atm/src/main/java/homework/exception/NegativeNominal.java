package homework.exception;

public class NegativeNominal extends RuntimeException {

    @Override
    public String getMessage() {
        return "Nominal should be always positive.";
    }

}
