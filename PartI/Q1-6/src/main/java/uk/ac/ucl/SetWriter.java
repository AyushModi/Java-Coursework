package uk.ac.ucl;

import java.io.*;
import java.util.Optional;

public class SetWriter {
    String filePath;
    MySet<?> set;
    private int depth = 1;

    public <T extends Comparable<T>> SetWriter(String filePath, MySet<T> set) {
        this.filePath = filePath;
        this.set = set;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public <T extends Comparable<T>> void setMySet(MySet<T> set) {
        this.set = set;
    }

    public void writeToFile() throws IOException, MySetException {
        Optional<String> className = getSetClass(set,1);
        if (className.isPresent())
            writeToFile(className.get());
        else {
            throw new MySetException("Cannot write an empty set");
        }
    }

    private void writeToFile(String className) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(className);
        writer.write(depth);
        writer.write(Integer.toString(depth));
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
}
