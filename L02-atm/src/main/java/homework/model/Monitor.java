package homework.model;

import homework.exception.ATMNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Monitor implements LowBalanceListener{

    private static final Logger logger = LoggerFactory.getLogger(Monitor.class);

    private final List<ATM> atmList;

    private final static int LIMIT = 1000;

    public Monitor(List<ATM> atmList) {
        this.atmList = atmList;
        atmList.forEach(i -> i.addListener(this, LIMIT));
    }

    @Override
    public void onAction(long idAtm, long balance) {
        logger.info("ATM {} has low balance: {}", idAtm, balance);
    }
}
