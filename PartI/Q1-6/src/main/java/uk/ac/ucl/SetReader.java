package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

public class SetReader<T extends Comparable<T>> {
    private MySetFactory factory = MySetFactory.getInstance();
    private Class<?> chosenClass;
    {
        factory.setClassName("LinkedListMySet");
    }

    public SetReader(Class chosenClass) {
        this.chosenClass = chosenClass;
    }

    public MySet<T> readFromFile(String filePath) throws IOException, ClassNotFoundException, MySetException, NumberFormatException {
        String setText, className = null;
        int depth;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            className = br.readLine();
            depth = Integer.parseInt(br.readLine());
            setText = br.readLine();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid set read: depth read is not of type integer");
        }

        runTypeSafetyChecks(setText, className, depth);

        try {
            return getSet(setText, depth);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Failed parsing " + className + " as " + chosenClass.getName());
        }
    }
    private void runTypeSafetyChecks(String setText, String className, int depth) throws ClassNotFoundException, MySetException {
        Class setClass = Class.forName(className);
        if (!Comparable.class.isAssignableFrom(setClass))
            throw new ClassNotFoundException("Class not comparable");
        if (!setClass.equals(chosenClass))
            System.out.println("Set in file has type: " + setClass.getName() +
                    "\nAttempting to read anyway. May produce errors");
        if (depth < 1 || (setText.charAt(0) != '{' && setText.charAt(setText.length() - 1) != '}'))
            throw new MySetException("Invalid set");
    }


    private Object parseToParam(String setText) {
        if (chosenClass.isAssignableFrom(String.class)) {
            return setText;
        } else if (chosenClass.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Double.class)) {
            return Double.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(setText.trim());
        } else if (chosenClass.isAssignableFrom(Character.class)) {
            return setText.charAt(0);
        } else {
            throw new IllegalArgumentException("Bad type.");
        }
    }

    @SuppressWarnings("unchecked")
//    private MySet<T> getSet(String setText, int depth) throws MySetException {
//        MySet<T> setCreated = factory.getMySet();
//        if (setText.equals("{}"))
//            return setCreated;
//        if (setText.charAt(0) != '{' && setText.charAt(setText.length()-1) != '}')
//            throw new MySetException("Invalid set");
//        StringBuilder subsetText = new StringBuilder();
//        char currentChar;
//        int i = 1;
//        int curly = 1;
//        boolean subsetEnded = false;
//
//        Function<Character, Function<Boolean, Boolean>> checkSubsetUnformed = (depth == 1)
//                ? c -> b -> (c != ',' && c != '}')
//                : c -> b -> !b;
//        while (i < setText.length()) {
//            currentChar = setText.charAt(i);
//            if (checkSubsetUnformed.apply(currentChar).apply(subsetEnded)) {
//                if (depth == 1 && currentChar == '{')
//                    throw new MySetException("Invalid set read");
//
//                subsetText.append(currentChar);
//                i++;
//                if (currentChar == '{')
//                    curly++;
//                else if (currentChar == '}' && --curly == 1)
//                    subsetEnded = true;
//                else if (currentChar == '\\')
//                    subsetText.append(setText.charAt(i++));
//            } else {
//                if (depth == 1) {
//                    setCreated.add((T) parseToParam(subsetText.toString()));
//                    i++;
//                } else {
//                    curly=1;
//                    setCreated.add((T) getSet(subsetText.toString(), depth - 1));
//                    while (++i < setText.length() && setText.charAt(i) != '{');
//                }
//                subsetText = new StringBuilder();
//                subsetEnded = false;
//            }
//        }
//        return setCreated;
//    }
    private MySet<T> getSet(String setText, int depth) throws MySetException {
        StringToSet.setChosenClass(chosenClass);
        StringToSet<T> s2s = new StringToSet<>(depth);
        s2s.setSetText(setText);
        return s2s.getSet();
    }
}
