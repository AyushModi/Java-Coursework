package uk.ac.ucl;

import java.io.*;
import java.util.Optional;

public class SetWriter {
    private String filePath;
    private MySet<?> set;
    private Class<?> chosenClassName;
    private int depth = 1;

    public <T extends Comparable<T>> SetWriter(String filePath, MySet<T> set) {
        this.filePath = filePath;
        this.set = set;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setClassName(Class className) {
        this.chosenClassName = className;
    }

    public <T extends Comparable<T>> void setMySet(MySet<T> set) {
        this.set = set;
    }

    public void writeToFile() throws IOException, MySetException, ClassNotFoundException {
        Optional<String> itemClassNameOptional = getSetClass(set, 1);
        if (itemClassNameOptional.isPresent()) {
            String className = itemClassNameOptional.get();
            if (chosenClassName != null) {
                if (!chosenClassName.isAssignableFrom(Class.forName(className))) {
                    System.out.println("Chosen class does not extend the class of the elements in the set. Attempting to write anyway as "
                            + chosenClassName + ". There might be problems when reading the file");
                }
                className = chosenClassName.getName();
            }
            writeToFile(className);
        } else {
            if (chosenClassName != null) {
                System.out.println("Writing empty set with type: " + chosenClassName);
                writeToFile(chosenClassName.getName());
            } else {
                throw new MySetException("Cannot write an empty set with no class specified");
            }
        }
    }

    private void writeToFile(String className) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(className);
        writer.newLine();
        writer.write(Integer.toString(depth));
        writer.newLine();
        writer.write(set.toString());
        writer.close();
    }

    private Optional<String> getSetClass(MySet set, int depth) {
        for (var item : set) {
            if (item == null) continue;
            if (item instanceof MySet) {
                Optional<String> temp = getSetClass((MySet) item, depth+1);
                if (!temp.isEmpty()) return temp;
            } else {
                this.depth = depth;
                return Optional.of(item.getClass().getName());
            }
        }
        return Optional.empty();
    }

//    public static void main(String[] args) {
//        MySetFactory factory = MySetFactory.getInstance();
//        factory.setClassName("LinkedListMySet");
//        try {
//            MySet<Integer> set = factory.getMySet();
//            set.add(1);
//            set.add(2);
//            set.add(3);
//            set.add(4);
//            SetWriter sw = new SetWriter("Sets/set3.txt", set.powerSet());
//            sw.writeToFile();
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }
}
