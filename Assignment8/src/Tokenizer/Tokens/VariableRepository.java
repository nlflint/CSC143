package Tokenizer.Tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by nate on 6/15/14.
 */
public class VariableRepository {
    private static HashMap<String,Double> variables = new HashMap<String, Double>();

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

    public static Set<String> getAllVariableNames() {
        return variables.keySet();
    }
}
