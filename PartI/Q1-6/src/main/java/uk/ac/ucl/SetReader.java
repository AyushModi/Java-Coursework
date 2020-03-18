package uk.ac.ucl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

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
            int depth = Integer.parseInt(br.readLine());
            String setText = br.readLine();

            Class setClass = Class.forName(className);
            if (!Comparable.class.isAssignableFrom(setClass))
                throw new ClassNotFoundException("Class not comparable");
            if (!setClass.equals(clazz))
                System.out.println("Set in file has type: " + setClass.getName() +
                        "\nAttempting to read anyway. May produce errors");
            if (setText.charAt(0) != '{' && setText.charAt(setText.length()-1) != '}')
                throw new MySetException("Invalid set");
            return getSet(setText, depth);
        } catch (NumberFormatException e) {
            throw new MySetException("Invalid set read");
        }
    }

    private Object parseToParam(String setText) {
        if (clazz.isAssignableFrom(String.class)) {
            return setText;
        } else if (clazz.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(setText.trim());
        } else if (clazz.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(setText.trim());
        } else if (clazz.isAssignableFrom(Double.class)) {
            return Double.valueOf(setText.trim());
        } else if (clazz.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(setText.trim());
        } else if (clazz.isAssignableFrom(Character.class)) {
            return setText.charAt(0);
        } else {
            throw new IllegalArgumentException("Bad type.");
        }
    }

    @SuppressWarnings("unchecked")
    private MySet<T> getSet(String setText, int depth) throws MySetException {
        MySet<T> setCreated = factory.getMySet();
        if (setText.charAt(0) != '{' && setText.charAt(setText.length()-1) != '}')
            throw new MySetException("Invalid set");
        StringBuilder subsetText = new StringBuilder();
        char temp, nextTemp ='_';
        int i = 1;
        int curly = 1;
        boolean subsetEnded = false;
//        Function<Character, Function<Character,Boolean>> condition = (depth == 1)
//                ? c -> (n -> (c != ',' && c != '}'))
//                : c -> (n -> !(c == '}' && (n == ',' || n == '}')));

        Function<Integer, Function<Boolean, Boolean>> condition = (depth == 1)
                ? j -> b -> (setText.charAt(j) != ',' && setText.charAt(j) != '}')
                : j -> b -> !b;
        while (i < setText.length()) {
            temp = setText.charAt(i);
//            if (condition.apply(i) && curly != 0) {
            if (condition.apply(i).apply(subsetEnded)) {
                if (depth != 1 || temp != '{') {
                    subsetText.append(temp);
                    if (temp == '{') curly++;
                    else if (temp == '}') {
                        curly--;
                        if (curly == 1)
                            subsetEnded = true;
                    }
                }
                i++;
                if (temp == '\\') {
                    subsetText.append(setText.charAt(i));
                    i++;
                }
            } else {
                if (subsetText.length() != 0) {
                    if (depth == 1) {
                        setCreated.add((T) parseToParam(subsetText.toString()));
                        i++;
                    } else {
//                        char[] chars = new char[curly];
                        curly=1;
//                        Arrays.fill(chars, '}');
//                        subsetText.append('}');
                        setCreated.add((T) getSet(subsetText.toString(), depth - 1));
                        while (++i < setText.length() && setText.charAt(i) != '{');

                    }
                } else {
                    if (depth > 1) {
                        setCreated.add((T) factory.getMySet());
                        while (++i < setText.length() && setText.charAt(i) != '{') ;
                    } else {
                        return setCreated;
                    }
                }
                subsetText = new StringBuilder();
                subsetEnded = false;
                if (depth == 3) {
                    int p=4;
                }
            }
        }
        return setCreated;
    }

//    public static void main(String[] args) {
//        SetReader<Integer> sr = new SetReader<>(Integer.class);
//        try {
//            MySet<Integer> setCreated = sr.readFromFile("Sets/set3.txt");
//            System.out.println("wah");
//        } catch (ClassNotFoundException | IOException | MySetException e) {
//            System.out.println(e.toString());
//        }
//    }

}
