package uk.ac.ucl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Example code illustrating the basic use of MySet objects.
 * Modify and extend this as you work on the questions.
 * Keep this class organised and well-written! (I would've if it was going to be graded)
 */
public class Main
{
  private MySetFactory factory = MySetFactory.getInstance();

    public <T extends Comparable<T>> void print(MySet<T> set)
  {
     System.out.println(set);
  }

  public  <T extends Comparable<T>> void checkOperations(MySet<T> set1, MySet<T> set2) throws MySetException
  {
    System.out.print("Set1: "+set1);
    System.out.print("\nSet2: "+set2);
    System.out.print("\nUnion: "+set1.union(set2));
    System.out.print("\nIntersection: "+set1.intersection(set2));
    System.out.print("\nDifference: "+set1.difference(set2));
    System.out.print("\nPowerset: "+set1.powerSet());
    System.out.println("\n");
  }

  public void checkIntSets()
  {
    System.out.println("Testing Integer sets");
    MySet<Integer> set1;
    MySet<Integer> set2;
    try
    {
      set1 = factory.getMySet();
      set1.add(3);
      set1.add(2);
      set1.add(1);
      System.out.println("Writing powerset of powerset of set1 to the file set1NestedInt.txt");
      set1.powerSet().powerSet().writeToFile("Sets/set1NestedInt.txt");
      set2 = factory.getMySet();
      set2.add(4);
      set2.add(3);
      set2.add(2);
      System.out.println("Writing powerset of powerset of set2 to the file set2NestedInt.txt");
      set2.powerSet().powerSet().writeToFile("Sets/set2NestedInt.txt");
      set1.compareTo(set2);
      checkOperations(set1, set2);

      System.out.println("Removing 3 from set1, 4 from set2");
      set1.remove(3);
      set2.remove(4);
      checkOperations(set1,set2);
      System.out.println("\n");
    }
    catch (MySetException e)
    {
      System.out.println("====> MySet Exception thrown...");
      System.out.println(e.toString());
    } catch (IOException e) {
      System.out.println("====> IO Exception thrown...");
    }
  }

  public void checkStringSets()
  {
    System.out.println("Testing String sets");
    MySet<String> set1;
    MySet<String> set2;
    try
    {
      set1 = factory.getMySet();
      set1.add("One, \"Fake\\\" Two");
      set1.add("Two\", Fake Three");
      set1.add("Three");
      System.out.println("Writing powerset of powerset of set1 to the file set5.txt");
      set1.powerSet().powerSet().writeToFile("Sets/set5.txt");
      set2 = factory.getMySet();
      set2.add("Two");
      set2.add("Three");
      set2.add("Four");
      checkOperations(set1, set2);
      System.out.println("Removing 'One' from set1, 'Four' from set2");
      set1.remove("One");
      set2.remove("Four");
      checkOperations(set1,set2);
      System.out.println("\n");
    }
    catch (MySetException e)
    {
      System.out.println("====> MySet Exception thrown...");
    } catch (IOException e) {
      System.out.println("====> IO Exception thrown...");
    }
  }

  public void go()
  {
//    factory.setClassName("ArrayMySet");
    factory.setClassName("LinkedListMySet");
//    factory.setClassName("MapMySet");

    checkIntSets();
    checkStringSets();

    try {
      System.out.println("Reading powerset of powerset of String set1 from file");
      MySet<String> setKaboom = factory.readSet("Sets/set5.txt", String.class);
      System.out.println(setKaboom);
      System.out.println("Size of string nested set that was just read in : " + setKaboom.size() + "\n");

      System.out.println("Reading powerset of powerset of Integer set1 and set2 from file");
      MySet<Integer> nestedInt1 = factory.readSet("Sets/set1NestedInt.txt", Integer.class);
      MySet<Integer> nestedInt2 = factory.readSet("Sets/set2NestedInt.txt", Integer.class);
      System.out.println("Powerset of powerset of set1: " + nestedInt1);
      System.out.println("Powerset of powerset of set2: " + nestedInt2);
      System.out.println("Read two nested int sets with sizes:" + nestedInt1.size() + " and " + nestedInt2.size());
      System.out.println("Their union is: \n" + nestedInt1.union(nestedInt2));
  } catch (Exception e) {
    System.out.println(e.toString());
  }
  }

  public static void main(String[] args)
  {
    new Main().go();
  }
}
