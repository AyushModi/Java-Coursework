package uk.ac.ucl;

import java.io.IOException;

/**
 * A factory to create MySet objects. The class is implemented as a Singleton, meaning that only one factory
 * object can be created. The factory can be configured with the name of the set class it creates instances
 * of.
 */
public class MySetFactory
{
    // The reference to the single factory object that can be created.
    // This is a class (static) variable, so there is only one copy belonging to the class.
    private static MySetFactory factory = null;

    // The name of the class that the factory will create objects of.
    private String className;

    // The constructor is private to prevent code in any other class creating an instance.
    private MySetFactory()
    {
    }

    /**
     * Return the single factory object, creating it if necessary. This is a static method that does
     * not need a factory object to be called on.
     * @return a reference to the factory object
     */
    public static MySetFactory getInstance()
    {
        if (factory == null) factory = new MySetFactory();
        return factory;
    }

    /**
     * Set the name of the class that the factory creates instances of.
     * @param aClass the name of the class.
     */
    public void setClassName(String aClass)
    {
        className = aClass;
    }

    /**
     * Create a set that is an instance of the class the factory has been set to create, with the maximum size
     * set to the default.
     * @return The new set.
     * @throws MySetException If the class is not recognised as one from
     * which a set object can be created.
     */
    public <T extends Comparable<T>> MySet<T> getMySet() throws MySetException
    {
        return getMySet(MySet.MAX_SIZE);
    }

    /**
     * Create a set that is an instance of the class the factory has been configured to create, with the
     * given maximum set size.
     * @param maxSize The maximum size of the new set.
     * @return The new set.
     * @throws MySetException If the class is not recognised as one from
     * which a set object can be created.
     */
    public <T extends Comparable<T>> MySet<T> getMySet(int maxSize) throws MySetException
    {
        switch (className)
        {
            case "ArrayMySet":
                return new ArrayMySet<>(maxSize);
            // Add additional cases here for set implementation classes as they are added.
            case "LinkedListMySet":
                return new LinkedListMySet<>(maxSize);
            case "MapMySet":
                return new MapMySet<>(maxSize);
            default:
                throw new MySetException
                        ("Attempting to use BagFactory to create something that is not a Bag");
        }
    }

    public <T extends Comparable<T>> MySet<T> readSet(String filePath, Class clzz) throws IOException, ClassNotFoundException, MySetException {
        SetReader<T> sr = new SetReader<>(clzz);
        return sr.readFromFile(filePath);
    }
}

