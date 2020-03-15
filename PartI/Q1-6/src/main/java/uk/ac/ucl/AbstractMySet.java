package uk.ac.ucl;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class implements methods common to all concrete set implementations but does not
 * represent a complete set implementation.<br />
 *
 * New set objects are created using a MySetFactory.
 */

public abstract class AbstractMySet<T extends Comparable<T>> implements MySet<T>
{
    @Override
    public String toString() {
        Function<T,String> stringFunction;
        if (this.iterator().next() instanceof String) {
            stringFunction = (s)->cleanString((String) s);
        } else {
            stringFunction = Object::toString;
        }
        return "{" +  this.toList().stream().map(stringFunction).collect(Collectors.joining(", ")) + "}";
    }
    private String cleanString(String str) {
        return "\"" + str.replace("\\","\\\\").replace("\"","\\\"") + "\"";
    }
    @Override
    public boolean equals(MySet<T> aSet)
    {
        if (aSet.size() != this.size()) return false;
        for (Iterator<T> i = aSet.iterator(); i.hasNext();) {
            if (!this.contains(i.next())) return false;
        }
        // TODO write a working method body.
        return true;
    }

    @Override
    public int hashCode()
    {
        return toList().hashCode();
    }

    public List<T> toList()
    {
        ArrayList<T> returnList = new ArrayList<>();
        this.iterator().forEachRemaining(returnList::add);
        // TODO write the code to return a List of list of the set contents.
        return returnList;
    }

    public MySet<T> union(MySet<T> mySet) throws MySetException
    {
        MySet<T> result = MySetFactory.getInstance().getMySet();
        addAll(this, result);
        for (Iterator<T> i = mySet.iterator(); i.hasNext();) {
            T nextVal = i.next();
            result.add(nextVal);
        }
        return result;
    }

    public MySet<T> intersection(MySet<T> mySet) throws MySetException
    {
        MySet<T> result = MySetFactory.getInstance().getMySet();
        for (T nextVal : mySet) {
            if (this.contains(nextVal)) {
                result.add(nextVal);
            }
        }
        // TODONE write the statements needed to find the intersection.

        return result;
    }

    public MySet<T> difference(MySet<T> mySet) throws MySetException
    {
        MySet<T> result = MySetFactory.getInstance().getMySet();
        for (Iterator<T> i = mySet.iterator(); i.hasNext();) {
            T nextVal = i.next();
            if (!this.contains(nextVal)) {
                result.add(nextVal);
            }
        }
        // TODONE write the statements needed to find the difference.
        return result;
    }

    @Override
    public int compareTo(MySet<T> aSet) {
        Collections.sort(aSet.toList());
        Collections.sort(this.toList());
        return this.toString().compareTo(aSet.toString());
    }

    public MySet<MySet<T>> powerSet() throws MySetException {
        MySet<MySet<T>> result = MySetFactory.getInstance().getMySet();
        result.add(MySetFactory.getInstance().getMySet());

        for (T item : this) {
            MySet<MySet<T>> temp = MySetFactory.getInstance().getMySet();

            // for each sEt in result
                // add item to sEt, add sEt to temp
            // add temp to result
//            result.iterator().forEachRemaining(r);
            for (MySet<T> set : result) {
                MySet<T> setClone = MySetFactory.getInstance().getMySet();
                addAll(set, setClone);
                setClone.add(item);
                temp.add(setClone);
            }
            result = result.union(temp);
//            result.add()
        }
        return result;
    }


    protected void checkSize(int maximumSize)
            throws MySetException
    {
        if (maximumSize < 1 || maximumSize > MAX_SIZE)
            throw new MySetException("Error: exceeds MAX_SIZE of MySet");
        // TODONE throw an exception if the set exceeds its maximum size.
    }

    // A helper method that might be useful.
    private void addAll(MySet<T> source, MySet<T> destination)
            throws MySetException
    {
        for (T value : source)
        {
            destination.add(value);
        }
    }

    public void writeToFile(String filePath) throws IOException {

        FileOutputStream fileOutput = new FileOutputStream(filePath);
        ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
        objectOutput.writeObject(this);
        objectOutput.close();
    }
}
