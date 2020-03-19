package uk.ac.ucl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Example code illustrating the basic use of MySet objects.
 * Modify and extend this as you work on the questions.
 * Keep this class organised and well-written!
 */
public class Main
{
  private MySetFactory factory = MySetFactory.getInstance();

    public <T extends Comparable<T>> void print(MySet<T> set)
  {
//    printSet3(set);
    // The statement below will work when toString is overridden.
     System.out.println(set);
  }

  public  <T extends Comparable<T>> void checkOperations(MySet<T> set1, MySet<T> set2) throws MySetException
  {
    System.out.print("Set1: ");
    print(set1);
    System.out.print("Set2: ");
    print(set2);
    System.out.print("Union: ");
    print(set1.union(set2));
    System.out.print("Intersection: ");
    print(set1.intersection(set2));
    System.out.print("Difference: ");
    print(set1.difference(set2));
    print(set1.powerSet());
  }

  public void checkIntSets()
  {
    MySet<Integer> set1;
    MySet<Integer> set2;
    try
    {
      set1 = factory.getMySet();
      set1.add(1);
      set1.add(2);
      set1.add(3);
      set1.powerSet().powerSet().writeToFile("set41.txt");
      set2 = factory.getMySet();
      set2.add(2);
      set2.add(3);
      set2.add(4);
      checkOperations(set1, set2);
      System.out.println("\nRemoving 3 from set1, 4 from set2");
      set1.remove(3);
      set2.remove(4);
      checkOperations(set1,set2);
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
    MySet<String> set1;
    MySet<String> set2;
    try
    {
      set1 = factory.getMySet();
      set1.add("One, \"Fake\\\" Two");
      set1.add("Two\", Fake Three");
      set1.add("Three");
      set1.powerSet().powerSet().writeToFile("Sets/set5.txt");

      set2 = factory.getMySet();
      set2.add("Two");
      set2.add("Three");
      set2.add("Four");
      checkOperations(set1, set2);
      System.out.println("\nRemoving 'One' from set1, 'Four' from set2");
      set1.remove("One");
      set2.remove("Four");
      checkOperations(set1,set2);
    }
    catch (MySetException e)
    {
      System.out.println("====> MySet Exception thrown...");
    } catch (IOException e) {
      System.out.println("====> IO Exception thrown...");
    }
  }
  public void writeComplicatedSets() {
    System.out.println("Writing sets");
      try {
        MySet<MySet<Date>> nestedSet = factory.getMySet();
        for (int numElems = 0; numElems < 5; numElems++) {
          MySet<Date> set = factory.getMySet();
          for (int i = 0; i < numElems; i++) {
            Date date = new Date();
            Thread.sleep(5000);
            set.add(date);
          }
          nestedSet.add(set);
        }
        nestedSet.writeToFile("Sets/complicatedSet.txt");
      } catch (MySetException e) {
        System.out.println(e.toString());
      } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
      } catch (IOException e) {
        System.out.println(e.toString());
      }
    System.out.println("done");
  }

  public void go()
  {
//    factory.setClassName("ArrayMySet");
    factory.setClassName("LinkedListMySet");
//    factory.setClassName("MapMySet");
    checkIntSets();
//    checkStringSets();
    try {
    MySet<String> setKaboom = factory.readSet("Sets/set5.txt", String.class);
    System.out.println("hi");
  } catch (Exception e) {
    System.out.println(e.toString());
  }
//    System.out.println("\n\n");
//    writeComplicatedSets();
  }

  public static void main(String[] args)
  {
    new Main().go();
  }
}
