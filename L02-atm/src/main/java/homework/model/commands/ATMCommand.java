package homework.model.commands;

import homework.model.ATM;

import java.util.Set;

@FunctionalInterface
public interface ATMCommand {

    void execute(Set<ATM> atmList);

}
