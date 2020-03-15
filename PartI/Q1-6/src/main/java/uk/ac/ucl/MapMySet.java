package uk.ac.ucl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class MapMySet<T extends Comparable<T>> extends AbstractMySet<T> implements Serializable
{
    private HashMap<T,T> contents;
    private int maximumSize;
    public MapMySet() throws MySetException
    {
        this(MAX_SIZE);
    }
    public MapMySet(int maximumSize) throws MySetException
    {
        checkSize(maximumSize);
        initialiseToEmpty(maximumSize);
    }
    private void initialiseToEmpty(int maximumSize)
    {
        this.maximumSize = maximumSize;
        contents = new HashMap<>();
    }
    public void add(T setItem) throws MySetException
    {
        if (setItem == null)
            throw new MySetException("Error: Cannot add null to MySet");
        if (contents.size() >= maximumSize)
            throw new MySetException("Attempting to add to full MySet");
        contents.putIfAbsent(setItem, setItem);
    }
    public boolean contains(T setItem)
    {
        return contents.containsValue(setItem);
    }
    public boolean isEmpty() { return contents.isEmpty(); }
    public void remove(T setItem) { contents.remove(setItem); }
    public int size() { return contents.size(); }
    //TODO keySet() returns a set
    public Iterator<T> iterator() {return contents.values().iterator(); }

}
