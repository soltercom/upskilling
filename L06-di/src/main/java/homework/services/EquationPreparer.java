package homework.services;

import homework.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
