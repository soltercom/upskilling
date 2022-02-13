package homework.model.commands;

import homework.model.ATM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ATMGetBalanceCommand implements ATMCommand {

    private static final Logger logger = LoggerFactory.getLogger(ATMGetBalanceCommand.class);

    @Override
    public void execute(Set<ATM> atmList) {
        atmList.forEach(atm -> {
            logger.info("ATM {} balance = {}", atm.getId(), atm.getBalance());
        });
    }
}
