package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SetReader<T extends Comparable<T>> {
    private MySetFactory factory = MySetFactory.getInstance();
    private Class<?> clazz;
    {
        factory.setClassName("LinkedListMySet");
    }

    public SetReader(Class clazz) {
        this.clazz = clazz;
    }
    public MySet<T> readFromFile(String filePath) throws IOException, ClassNotFoundException, MySetException {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String className = br.readLine();
            Class setClass = Class.forName(className);
            int depth = Integer.parseInt(br.readLine());
            if (!setClass.equals(clazz))
                throw new MySetException("Set in file has type: " + setClass.getName());
            if (!Comparable.class.isAssignableFrom(setClass))
                throw new ClassNotFoundException("Class not comparable");

            String setText = br.readLine();
            return getSet(setText, depth);
        } catch (NumberFormatException e) {
            throw new MySetException("Invalid set read");
        }
    }

    private Object parseToParam(String setText) {
        if (clazz.isAssignableFrom(String.class)) {
            return setText;
        } else if (clazz.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(setText);
        } else if (clazz.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(setText);
        } else if (clazz.isAssignableFrom(Double.class)) {
            return Double.valueOf(setText);
        } else {
            throw new IllegalArgumentException("Bad type.");
        }
    }

    private MySet<T> getSet(String setText, int depth) throws MySetException {

        MySet<T> setCreated = factory.getMySet();
        if (setText.charAt(0) != '{')
            throw new MySetException("Invalid set");
        if (depth == 1) {
            setText = setText.substring(1);
            StringBuilder subText = new StringBuilder();
            char temp;
            int i = 0;
            while (i < setText.length()) {
                if ((temp=setText.charAt(i)) != ',' && temp != '}') {
                    subText.append(temp);
                    i++;
                    if (temp == '\\') {
                        subText.append(subText.charAt(i));
                        i++;
                    }
                } else {
                    if (subText.length() != 0) {
                        setCreated.add((T) parseToParam(subText.toString()));
                    }
                    subText = new StringBuilder();
                    i++;
                }
            }
        } else {
            setText = setText.substring(1);
            StringBuilder subText = new StringBuilder();
            char temp;
            int i = 0;

            while (i < setText.length()) {
                if (!((temp=setText.charAt(i)) == '}' && (setText.charAt(i+1) == ',' || setText.charAt(i+1) == '}'))) {
                    subText.append(temp);
                    i++;
                    if (temp == '\\') {
                        subText.append(temp);
                        i++;
                    }
                } else {
                    if (subText.length() != 0) {
                        setCreated.add((T) getSet(subText.toString() + "}", depth - 1));
                    }
                    subText = new StringBuilder();
                    i+=2;
                }
            }

        }
        return setCreated;
    }

    public static void main(String[] args) {
        SetReader<Integer> sr = new SetReader<>(Integer.class);
        try {
            MySet<Integer> setCreated = sr.readFromFile("Sets/set2.txt");
            System.out.println("wah");
        } catch (ClassNotFoundException | IOException | MySetException e) {
            System.out.println(e.toString());
        }
    }

}
