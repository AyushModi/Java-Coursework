package uk.ac.ucl;

import java.util.Iterator;
import java.util.ArrayList;

/**
 *  A set implementation using an ArrayList to store the set contents.
 * <br />
 *  Objects representing values stored in a set are compared using the compareTo method, so
 *  the object classes must correctly implement compareTo.
 */
public class ArrayMySet<T extends Comparable<T>> extends AbstractMySet<T>
{
  private ArrayList<T> contents;
  private int maximumSize;

  public ArrayMySet()  throws MySetException
  {
    this(MAX_SIZE);
  }

  public ArrayMySet(int maximumSize) throws MySetException
  {
    checkSize(maximumSize);
    initialiseToEmpty(maximumSize);
  }

  private void initialiseToEmpty(int maximumSize)
  {
    this.maximumSize = maximumSize;
    contents = new ArrayList<>();
  }

  public void add(T value) throws MySetException
  {
    if (!contents.contains(value))
    {
      checkForSpace();
      contents.add(value);
    }
  }

  private void checkForSpace() throws MySetException
  {
    if (maximumSize == contents.size())
    {
      throw new MySetException("Attempting to add to full MySet");
    }
  }

  public boolean contains(T value)
  {
    for (T setValue : contents)
    {
      if (setValue.compareTo(value) == 0)
      {
        return true;
      }
    }
    return false;
  }

  public boolean isEmpty()
  {
    return contents.isEmpty();
  }

  public void remove(T value)
  {
    contents.remove(value);
  }

  public int size()
  {
    return contents.size();
  }

  // A nested member (inner) class implementing an iterator, with access to the private
  // scope of the ArrayMySet class.
  private class ArrayMySetIterator implements Iterator<T>
  {
    private int index = 0;
    public boolean hasNext()
    {
      return index < contents.size();
    }

    public T next()
    {
      return contents.get(index++);
    }
  }

  public Iterator<T> iterator()
  {
    return new ArrayMySetIterator();
  }
}

