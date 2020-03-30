package uk.ac.ucl;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * This function will escape illegal characters that can create confusion if this set was to be read from a file
     * @param str The string to be escaped
     * @return The escaped string
     */
    private String cleanString(String str) {
        return (str.replace("\\","\\\\")
                   .replace("\"","\\\"")
                   .replace("{","\\{")
                   .replace("}","\\}")
                   .replace(",","\\,"));
    }
    @Override
    public boolean equals(MySet<T> aSet)
    {
        if (aSet.size() != this.size()) return false;
        for (Iterator<T> i = aSet.iterator(); i.hasNext();) {
            if (!this.contains(i.next())) return false;
        }
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
        return result;
    }

    /**
     * Standard compareTo function specifying that sets will be compared on the basis of their sizes
     * @param aSet The specified set to compare with
     * @return an int being -1 if smaller, 1 if bigger, 0 if equal length.
     */
    @Override
    public int compareTo(MySet<T> aSet) {
        if (this.size() < aSet.size())
            return -1;
        else if (this.size() > aSet.size())
            return 1;
        else {
            return 0;
        }
    }

    public MySet<MySet<T>> powerSet() throws MySetException {
        MySet<MySet<T>> result = MySetFactory.getInstance().getMySet();
        result.add(MySetFactory.getInstance().getMySet());

        for (T item : this) {
            MySet<MySet<T>> temp = MySetFactory.getInstance().getMySet();
            for (MySet<T> set : result) {
                MySet<T> setClone = MySetFactory.getInstance().getMySet();
                addAll(set, setClone);
                setClone.add(item);
                temp.add(setClone);
            }
            result = result.union(temp);
        }
        return result;
    }


    protected void checkSize(int maximumSize)
            throws MySetException
    {
        if (maximumSize < 1 || maximumSize > MAX_SIZE)
            throw new MySetException("Error: exceeds MAX_SIZE of MySet");
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

    public void writeToFile(String filePath) throws IOException, MySetException {
        SetWriter sw = new SetWriter(filePath, this);
        try {
            sw.writeToFile();
        } catch (ClassNotFoundException e) {
            System.out.println("Error occurred while writing set\n" + e.toString());
        }
    }
    public void writeToFile(String filePath, Class<T> className) throws IOException, MySetException, ClassNotFoundException {
        SetWriter sw = new SetWriter(filePath, this);
        sw.setClassName(className);
        sw.writeToFile();
    }
}
