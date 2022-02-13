package homework.model;

public interface LowBalanceListener {

    void onAction(long idAtm, long balance);

}
