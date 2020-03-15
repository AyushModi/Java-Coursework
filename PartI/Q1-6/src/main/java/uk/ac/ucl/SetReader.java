package uk.ac.ucl;

import java.io.*;

public class SetReader {

    public MySet<? extends Comparable> readFromFile(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object objectRead = ois.readObject();
        ois.close();
        return (MySet<? extends Comparable>) Class.forName(objectRead.getClass().getName()).cast(objectRead);
    }
    public static void main(String[] args) {
    }

}
