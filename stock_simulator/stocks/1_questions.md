# Question 1

Suppose you are developing a similar (if not identical) project for a company. One teammate poses the following:

> "We do not have to worry about logging. The application is very small and tests should take care of any potential bugs. If we really need it, we can print some important data and just comment it out later."

Do you agree or disagree with the proposition? Please elaborate on your reason to agree or disagree. (~50-100 words)

___

**Answer**:
We disagree with this statement. Logging in Java can make it easier to spot 
errors and debug them if needed. This is important no matter how small the
project is. 

We disagree because logging events and messages can help identify errors that 
may cause the program to run incorrectly. Preventing errors at the start helps 
ensure that these smaller errors will not cause even bigger issues if when the 
project is bigger.

Logging also helps with performance monitoring. The developers can use logging 
to measure how long an action takes to complete which can help prevent a program
that is too slow in the future.
___

# Question 2

Suppose you have the following `LinkedList` implementation:

![LinkedList](images/LinkedList.png)

How could you modify the `LinkedList` class so that the value could be any different data type? Preferably, provide the code of the modified class in the answer.
___

**Answer**:
We can modify the `LinkedList` class by using generics.

```java
import lombok.Getter;
public class Node<T> {
    @Getter
    private final T value;
    @Getter @Setter
    private final Node<T> next;

    public Node(T value) {
        this.value = value;
        this.next = null;
    }
}

public class LinkedList<T> {
    private Node<T> head;
    @Getter
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void insert(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(node);
        }
        size++;
    }

    public void delete(T value) {
        if (head == null) {
            return;
        }
        current = head;
        while (current.getNext.getNext != null) {
            current = current.getNext();
        }
        current.setNext(null);
    }
}


```

___

# Question 3

How is Continuous Integration applied to (or enforced on) your assignment? (~30-100 words)

___

Continuous Integration is enforced on our assignment by the fact that we have been asked to break down the problem into 
sub-problems and working and testing each one of them separately. As we progress, we integrate the different parts we 
have worked on and test them to make sure they work together. 
___

# Question 4

One of your colleagues wrote the following class:

```java
import java.util.*;

public class MyMenu {

    private final Map<Integer, PlayerAction> actions;

    public MyMenu() {
        actions = new HashMap<>();
        actions.put(0, DoNothingAction());
        actions.put(1, LookAroundAction());
        actions.put(2, FightAction());
    }

    public void printMenuOptions(boolean isInCombat) {
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("What do you want to?");
        menuOptions.add("\t0) Do nothing");
        menuOptions.add("\t1) Look around");
        if(isInCombat) {
            menuOptions.add("\t2) Fight!");
        }
        menuOptions.forEach(System.out::println);
    }

    public void doOption() {
        int option = getNumber();
        if(actions.containsKey(option)) {
            actions.get(option).execute();
        }
    }

    public int getNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
```
List at least 2 things that you would improve, how it relates to test-driven development and why you would improve these things. Provide the improved code below.

___

**Answer**:

- Rename the getNumber() function to something more descriptive like getOptionInput(). This is because it could be
confusing to other people working on this document
- The function getNumber() and doOption() do not account for if a number is out of bounds. If a user inputs an incorrect
number, the program does nothing and simply stops working, without informing the user. To make this better, the user should be
continuously prompted to input their number.
- The constructor does not follow the basic principles of OOP. It is a very fixed constructor which makes it harder
to update or edit in the future. It is more difficult to extend it. This can be fixed by having a separate method for that
so new options can be added.
- We would have avoided creating the print function in this way and instead keep the creation of the list in the construction of the class. 
The way it has been written causes the code to create a new array list every time the function is called as well as create the same
variables over and over again. This is not time and space efficient.


Improved code:

```java

import java.util.*;

public class MyMenu {

    private final Map<Integer, PlayerAction> actions;
    private List<String> menuOptions;

    public MyMenu() {
        actions = new HashMap<>();
        List<String> menuOptions = new ArrayList<>();
    }

    //can add more options if needed
    public void setMenuOptions() {
        actions.put(0, DoNothingAction());
        actions.put(1, LookAroundAction());
        actions.put(2, FightAction());
        menuOptions.add("What do you want to?");
        menuOptions.add("\t0) Do nothing");
        menuOptions.add("\t1) Look around");
    }

    public void printMenuOptions(boolean isInCombat) {
        if (isInCombat) {
            menuOptions.add("\t2) Fight!");
        }
        menuOptions.forEach(System.out::println);
    }

    public void doOption() {
        int option = getNumber();
        while (!actions.containsKey(option)) {
            option = getNumber();
        }
        actions.get(option).execute();
    }

    public int getNumberInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
```
___