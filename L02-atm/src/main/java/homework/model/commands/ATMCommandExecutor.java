package homework.model.commands;

import homework.model.ATM;

import java.util.*;

public class ATMCommandExecutor {

    private final Set<ATM> atmList = new HashSet<>();

    private final Queue<ATMCommand> commandQueue = new ArrayDeque<>();

    public void addReceiver(ATM atm) {
        atmList.add(atm);
    }

    public void addCommand(ATMCommand command) {
        commandQueue.add(command);
    }

    public void executeCommands() {
        while (commandQueue.peek() != null) {
            commandQueue.poll().execute(atmList);
        }
    }
}
