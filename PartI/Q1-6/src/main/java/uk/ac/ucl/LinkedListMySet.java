package uk.ac.ucl;

import java.io.Serializable;
import java.util.Iterator;

public class LinkedListMySet<T extends Comparable<T>> extends AbstractMySet<T> implements Serializable
{
    private int maximumSize;
    private ListElement<T> head;
    private int numElements;
    public LinkedListMySet() throws MySetException
    {
        this(MAX_SIZE);
    }
    public LinkedListMySet(int maximumSize) throws MySetException
    {
        checkSize(maximumSize);
        initialiseToEmpty(maximumSize);
    }
    private void initialiseToEmpty(int maximumSize)
    {
        this.maximumSize = maximumSize;
        this.numElements = 0;
        this.head = new ListElement<>(null,null);
    }
    public void add(T value) throws MySetException
    {
        if (numElements >= maximumSize) {
            throw new MySetException("Error: Set is full");
        } else if (value == null) {
            throw new MySetException("Error: null cannot be added to the set");
        } else {
            numElements += head.addToTailIfUnique(value);
        }
    }
    public void remove(T removeValue) {
        removeRecurse(removeValue,null, head);
    }
    private void removeRecurse(T removeValue,ListElement<T> prev, ListElement<T> current) {
        if (current == null || current.getValue() == null) return;
        if (current.getValue().equals(removeValue)) {
            if (prev == null) {
                head = current.getNext();
            } else {
                prev.setNext(current.getNext());
            }
            --numElements;
            return;
        }
        if (current.getNext() != null)
            removeRecurse(removeValue, current, current.getNext());
    }
    public boolean contains(T value)
    {
        for (Iterator<T> i = iterator(); i.hasNext();) {
            if (value.equals(i.next())) return true;
        }
        return false;
    }

    public boolean isEmpty() { return numElements==0; }
    public int size() { return numElements; }
    public Iterator<T> iterator() { return new LinkedListMySetIterator(); }

    private class LinkedListMySetIterator implements Iterator<T>
    {
        private ListElement<T> current = head;
        public boolean hasNext() {return (current != null && current.value != null);}

        public T next() {
            T returnVal = current.getValue();
            current = current.getNext();
            return returnVal;
        }
    }
    private static class ListElement<E> implements Serializable
    {
        private E value;
        private ListElement<E> next;
        public ListElement(E value, ListElement<E> next)
        {
            this.setValue(value);
            this.setNext(next);
        }

        public E getValue() { return value; }

        public void setValue(E value) { this.value = value; }

        public ListElement<E> getNext() { return next; }

        public void setNext(ListElement<E> next) { this.next = next; }

        public int addToTailIfUnique(E addValue) {
            if (this.getValue() == null) {
                this.setValue(addValue);
                return 1;
            }
            if (this.getValue().equals(addValue))
                return 0;

            if (this.getNext() != null) {
                return this.getNext().addToTailIfUnique(addValue);
            } else {
                this.setNext(new ListElement<>(addValue, null));
                return 1;
            }
        }
    }
}
