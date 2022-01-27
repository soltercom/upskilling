package homework.service;

import homework.exception.OutOfMoney;
import homework.model.ATM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ATMServiceImpl implements ATMService {

    private static final Logger logger = LoggerFactory.getLogger(ATMServiceImpl.class);

    private final ATM atm;

    public ATMServiceImpl(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void take(int nominal, int quantity) {
        logger.info("Before take balance = {}", atm.getBalance());
        logger.info("taken = {}", nominal * quantity);
        atm.take(nominal, quantity);
        logger.info("After take balance = {}", atm.getBalance());
    }

    @Override
    public void give(long sum) throws OutOfMoney {
        logger.info("Before give balance = {}", atm.getBalance());
        logger.info("given = {}", sum);
        atm.give(sum);
        logger.info("After give balance = {}", atm.getBalance());
    }

    @Override
    public long getBalance() {
        logger.info("Balance = {}", atm.getBalance());
        return atm.getBalance();
    }

}
