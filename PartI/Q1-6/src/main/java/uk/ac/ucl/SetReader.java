package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SetReader<T extends Comparable<T>> {
    private Class<?> chosenClass;

    public SetReader(Class chosenClass) {
        this.chosenClass = chosenClass;
    }

    public MySet<T> readFromFile(String filePath) throws IOException, ClassNotFoundException, MySetException, NumberFormatException {
        String setText, className;
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



    private MySet<T> getSet(String setText, int depth) throws MySetException {
        StringToSet.setChosenClass(chosenClass);
        StringToSet<T> s2s = new StringToSet<>(depth);
        s2s.setSetText(setText);
        return s2s.getSet();
    }
}
