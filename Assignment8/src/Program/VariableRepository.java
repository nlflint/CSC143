package Program;

import java.util.*;

/**
 * Created by nate on 6/15/14.
 */
public class VariableRepository {
    private LinkedHashMap<String,Double> variables = new LinkedHashMap<String, Double>();

    public void setVariableValue(String name, double value) {
        variables.put(name, value);
    }

    public double getVariableValue(String name) {
        return variables.get(name);
    }

    public boolean isVariableDefined(String name) {
        return variables.containsKey(name);
    }

    public void removeVariable(String name) {
        variables.remove(name);
    }

    public List<String> getAllVariableNames() {
        ArrayList<String> variableNames = new ArrayList<String>();
        variableNames.addAll(variables.keySet());
        return variableNames;
    }

    public void deleteAllVariables() {
        variables.clear();
    }
}
