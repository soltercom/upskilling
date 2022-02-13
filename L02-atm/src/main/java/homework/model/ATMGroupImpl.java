package homework.model;

import homework.exception.ATMNotFound;
import homework.model.commands.ATMCommandExecutor;
import homework.model.commands.ATMGetBalanceCommand;
import homework.model.commands.ATMOfflineCommand;
import homework.model.commands.ATMOnlineCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ATMGroupImpl implements ATMGroup {

    private static final Logger logger = LoggerFactory.getLogger(ATMGroupImpl.class);

    private final Map<Long, ATMState> savePoints;

    private final Map<Long, ATM> group;

    private final ATMCommandExecutor cmd = new ATMCommandExecutor();

    public ATMGroupImpl() {
        this.group = new HashMap<>();
        this.savePoints = new HashMap<>();
    }

    public void addATM(ATM atm) {
        group.put(atm.getId(), atm);
        cmd.addReceiver(atm);
    }

    @Override
    public void save() {
        logger.info("Save points started...");
        for (var entry: group.entrySet()) {
            savePoints.put(entry.getKey(), entry.getValue().save());
        }
        logger.info("Save points done.");
    }

    @Override
    public void restore() {
        logger.info("Recovery started...");
        for (var entry: savePoints.entrySet()) {
            addATM(Factory.createATM(entry.getKey(), entry.getValue()));
        }
        logger.info("Recovery done...");
    }

    @Override
    public void online() {
        cmd.addCommand(new ATMOnlineCommand());
        cmd.executeCommands();
    }

    @Override
    public void offline() {
        cmd.addCommand(new ATMOfflineCommand());
        cmd.executeCommands();
    }

    @Override
    public void getBalance() {
        cmd.addCommand(new ATMGetBalanceCommand());
        cmd.executeCommands();
    }

    @Override
    public long getBalance(long id) {
        return group.values().stream()
            .filter(atm -> atm.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ATMNotFound(id))
            .getBalance();
    }

}
