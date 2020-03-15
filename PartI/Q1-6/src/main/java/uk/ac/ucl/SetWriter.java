package uk.ac.ucl;

import java.io.*;

public class SetWriter<T extends Comparable<T>> {
    String filePath;
    MySet<T> set;
    public SetWriter(String filePath, MySet<T> set) {
        this.filePath = filePath;
        this.set = set;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setMySet(MySet<T> set) {
        this.set = set;
    }

//    public void writeToFile(String filePath) throws IOException {
////        FileWriter out = new FileWriter("./" + filePath);
////        out.write(set.iterator().next().getClass().toString());
////        out.write(set.toString());
////        out.close();
//        FileOutputStream fos = new FileOutputStream(filePath);
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//        oos.writeObject(set);
//        oos.close();
//    }


    public static void main(String[] args) {
        //TODO change var names
        MySetFactory factory = MySetFactory.getInstance();
        factory.setClassName("MapMySet");
        try {
            MySet<String> set = factory.getMySet();
            set.add("String1");
            set.add("String2");
            set.add("wait");
            set.writeToFile("Sets/sets.txt");
        } catch (MySetException e) {
            System.out.println("set error caught");
        } catch (IOException e) {
            System.out.println("io error caught");
            System.out.println(e.toString());
        }
    }
}
