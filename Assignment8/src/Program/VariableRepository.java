package Program;

import java.util.*;

/**
 * Manages the values stored in variables.
 */
public class VariableRepository {
    private LinkedHashMap<String,Double> variables = new LinkedHashMap<String, Double>();

    /**
     * Saves the given variable name with the given value. If the variable doesn't exist
     * then it will be created. If the variable exists, then it will be overwritten with
     * the new value.
     * @param name the name of a variable
     * @param value the new value of the variable
     */
    public void setVariableValue(String name, double value) {
        variables.put(name, value);
    }

    /**
     * Retrieves the value of the given variable name. If the variable doesn't exist an exception
     * will be thrown.
     * @param name name of the variable
     * @return the value of the given variable
     */
    public double getVariableValue(String name) {
        return variables.get(name);
    }

    /**
     * Identifies if the given variable exists.
     * @param name the name of the variable
     * @return true if the variable exists. False if it does not exist.
     */
    public boolean isVariableDefined(String name) {
        return variables.containsKey(name);
    }

    /**
     * Removes the given variable name and its value.
     * @param name the name of the variable.
     */
    public void removeVariable(String name) {
        variables.remove(name);
    }

    /**
     * Identifies all known variables.
     * @return a list of variable names
     */
    public List<String> getAllVariableNames() {
        ArrayList<String> variableNames = new ArrayList<String>();
        variableNames.addAll(variables.keySet());
        return variableNames;
    }
}
