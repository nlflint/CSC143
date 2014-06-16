package Tokenizer.Tokens;

import java.util.*;

/**
 * Created by nate on 6/15/14.
 */
public class VariableRepository {
    private static LinkedHashMap<String,Double> variables = new LinkedHashMap<String, Double>();

    public static void setVariableValue(String name, double value) {
        variables.put(name, value);
    }

    public static double getVariableValue(String name) {
        return variables.get(name);
    }

    public static boolean isVariableDefined(String name) {
        return variables.containsKey(name);
    }

    public static void removeVariable(String name) {
        variables.remove(name);
    }

    public static List<String> getAllVariableNames() {
        ArrayList<String> variableNames = new ArrayList<String>();
        variableNames.addAll(variables.keySet());
        return variableNames;
    }

    public static void deleteAllVariables() {
        variables.clear();
    }
}
