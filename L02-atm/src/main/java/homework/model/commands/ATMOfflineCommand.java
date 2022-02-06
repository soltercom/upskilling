package homework.model.commands;

import homework.model.ATM;

import java.util.Set;

public class ATMOfflineCommand implements ATMCommand {
    @Override
    public void execute(Set<ATM> atmList) {
        atmList.forEach(ATM::offline);
    }
}
