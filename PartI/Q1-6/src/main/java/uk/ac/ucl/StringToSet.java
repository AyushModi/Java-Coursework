package uk.ac.ucl;

import java.util.function.Function;
import java.util.function.Supplier;

public class StringToSet<T extends Comparable<T>> {
    private static Class<?> chosenClass;
    private MySetFactory factory = MySetFactory.getInstance();
    private String setText;
    private int depth;
    private MySet<T> setCreated;
    private char currentChar;
    private int curly = 1;
    private int i = 1;
    private boolean subsetEnded = false;
    private StringBuilder subsetText = new StringBuilder();

    public StringToSet(int depth) throws MySetException {
        setCreated = factory.getMySet();
        this.depth = depth;

    }

    public static void setChosenClass(Class<?> chosenClass) {
        StringToSet.chosenClass = chosenClass;
    }

    public void setSetText(String setText) {
        this.setText = setText;
    }

    public MySet<T> getSet() throws MySetException {
        if (setText.equals("{}"))
            return setCreated;
        runSetChecks();
        while (i < setText.length()) {
            currentChar = setText.charAt(i);
            if (subsetIncomplete()) {
                subsetText.append(currentChar);
                i++;
                runCurrentCharChecks();
            } else if (depth == 1) {
                setCreated.add(parseToParam(subsetText.toString()));
                i++;
                resetVariables();
            } else {
                addFromInnerSet();
                while (++i < setText.length() && setText.charAt(i) != '{') ;
                resetVariables();
            }
        }
        return setCreated;
    }

    @SuppressWarnings("unchecked")
    private void addFromInnerSet() throws MySetException {
        StringToSet<T> innerSet = new StringToSet<>(depth - 1);
        innerSet.setSetText(subsetText.toString());
        setCreated.add((T) innerSet.getSet());
    }

    private void resetVariables() {
        subsetText = new StringBuilder();
        subsetEnded = false;
        curly = 1;
    }

    private Boolean subsetIncomplete() {
        return (depth == 1 && currentChar != ',' && currentChar != '}')
                || (depth != 1 && !subsetEnded);
    }

    private void runSetChecks() throws MySetException {
        if (setText.charAt(0) != '{' && setText.charAt(setText.length() - 1) != '}')
            throw new MySetException("Invalid set");
        if (depth == 1 && setText.substring(1).contains("{"))
            throw new MySetException("Invalid set read");
    }

    private void runCurrentCharChecks() {
        if (currentChar == '{')
            curly++;
        else if (currentChar == '}' && --curly == 1)
            subsetEnded = true;
        else if (currentChar == '\\')
            subsetText.append(setText.charAt(i++));
    }

    @SuppressWarnings("unchecked")
    private T parseToParam(String setText) {
        if (chosenClass.isAssignableFrom(String.class)) {
            return (T) setText;
        } else if (chosenClass.isAssignableFrom(Integer.class)) {
            return (T) Integer.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Boolean.class)) {
            return (T) Boolean.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Double.class)) {
            return (T) Double.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Boolean.class)) {
            return (T) Boolean.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Character.class)) {
            return (T) Character.valueOf(setText.charAt(0));
        } else {
            throw new IllegalArgumentException("Bad type.");
        }

    }


}
