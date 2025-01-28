# Question 1

In the assignment, you had to create a `nl.rug.aoop.networking.client.MessageHandler` interface. Please answer the following two questions:

1. Describe the benefits of using this `nl.rug.aoop.networking.client.MessageHandler` interface. (~50-100 words)
2. Instead of creating an implementation of `nl.rug.aoop.networking.client.MessageHandler` that invokes a command handler, we could also pass the command handler to the client/server directly without the middle man of the `nl.rug.aoop.networking.client.MessageHandler` implementation. What are the implications of this? (~50-100 words)

___

**Answer**:
1. It is good practice to use this interface as it allows for the client to be more flexible. This allows for the client to be able to handle different types of messages. For example, if the client wanted to handle a message that was not a command, it could do so by implementing the interface and overriding the handle method. This isn't a current necessity since the program is at a very short scale, however it would be better practice to allow expendability and code maintenance. This is due to reducing code duplicataion and increasing readability.
2. Passing the command handler directly to the client/server without the middle man of the implementation  would make the client/server more tightly coupled. This would again complicate expenditure and maintenance since it will require many more changes in the code. Additionally, it would make it more difficult to test the client/server, as we would need to mock the command handler directly instead of mocking the MessageHandler interface.
___

# Question 2

One of your colleagues wrote the following class:

```java
public class RookieImplementation {

    private final Car car;

    public RookieImplementation(Car car) {
        this.car = car;
    }

    public void carEventFired(String carEvent) {
        if("steer.left".equals(carEvent)) {
            car.steerLeft();
        } else if("steer.right".equals(carEvent)) {
            car.steerRight();
        } else if("engine.start".equals(carEvent)) {
            car.startEngine();
        } else if("engine.stop".equals(carEvent)) {
            car.stopEngine();
        } else if("pedal.gas".equals(carEvent)) {
            car.accelerate();
        } else if("pedal.brake".equals(carEvent)) {
            car.brake();
        }
    }
}
```

This code makes you angry. Briefly describe why it makes you angry and provide the improved code below.

___

**Answer**:

There is a couple of different ways the code can be improved. The first way is using switch statements instead of 
'if' statements. Switch cases are considered cleaner. 

The second way is using the Command Design Pattern. If we created an interface with the method execute(), 
then every car command could implement it and perform its respective action(eg. a class EngineStartCommand 
would implement the interface and override the execute method with car.enginerStart()). Then
the RookieImplementation(of which the name is also not very proper as it does say anything about what 
the class should do) could simply call the execute method for any command that is passed in. 

Improved code:

```java
//IMPLEMENTATION 1
switch (carEvent) {
	case STEER_LEFT:
		car.steerLeft();
		break;
	case STEER_RIGHT:
		car.steerRight();
		break;
	case ENGINE_START:
		car.startEngine();
		break;
	case ENGINE_STOP:
		car.stopEngine();
		break;
	case PEDAL_GAS:
		car.accelerate();
		break;
	case PEDAL_BRAKE:
		car.brake();
		break;
}
    
    //IMPLEMENTATION 2
public interface CarCommand {
	void execute();
}

public class EngineStartCommand implements CarCommand {
	private final Car car;

	public EngineStartCommand(Car car) {
		this.car = car;
	}

	@Override
	public void execute() {
		car.engineStart();
	}
}

public class RookieImplementation {
	private final Car car;

	public RookieImplementation(Car car) {
		this.car = car;
	}

	public void carEventFired(CarCommand carCommand) {
		carCommand.execute();
	}
}

```
___

# Question 3

You have the following exchange with a colleague:

> **Colleague**: "Hey, look at this! It's super handy. Pretty simple to write custom experiments."

```java
class Experiments {
    public static Model runExperimentA(DataTable dt) {
        CommandHandler commandSequence = new CleanDataTableCommand()
            .setNext(new RemoveCorrelatedColumnsCommand())
            .setNext(new TrainSVMCommand())

        Config config = new Options();
        config.set("broadcast", true);
        config.set("svmdatatable", dt);

        commandSequence.handle(config);

        return (Model) config.get("svmmodel");
    }

    public static Model runExperimentB() {
        CommandHandler commandSequence = new CleanDataTableCommand()
            .setNext(new TrainSGDCommand())

        Config config = new Options();
        config.set("broadcast", true);
        config.set("sgddatatable", dt);

        commandSequence.handle(config);

        return (Model) config.get("sgdmodel");
    }
}
```

> **Colleague**: "I could even create this method to train any of the models we have. Do you know how Jane did it?"

```java
class Processor {
    public static Model getModel(String algorithm, DataTable dt) {
        CommandHandler commandSequence = new TrainSVMCommand()
            .setNext(new TrainSDGCommand())
            .setNext(new TrainRFCommand())
            .setNext(new TrainNNCommand())

        Config config = new Options();
        config.set("broadcast", false);
        config.set(algorithm + "datatable", dt);

        commandSequence.handle(config);

        return (Model) config.get(algorithm + "model");
    }
}
```

> **You**: "Sure! She is using the command pattern. Easy indeed."
>
> **Colleague**: "Yeah. But look again. There is more; she uses another pattern on top of it. I wonder how it works."

1. What is this other pattern? What advantage does it provide to the solution? (~50-100 words)

2. You know the code for `CommandHandler` has to be a simple abstract class in this case, probably containing four methods:
- `CommandHandler setNext(CommandHandler next)` (implemented in `CommandHandler`),
- `void handle(Config config)` (implemented in `CommandHandler`),
- `abstract boolean canHandle(Config config)`,
- `abstract void execute(Config config)`.

Please provide a minimum working example of the `CommandHandler` abstract class.

___

**Answer**:
1.
The factory pattern is used here in the creation of config. The multiple method calls .set
resembles the factory pattern. This pattern allows for code to be easily modified and easier to extend.
Of course, that means that it makes it less coupled as well. The factory pattern is a good solution if you want to
easily extend your code (as seen by doing '.set').
2.
```java
public abstract class CommandHandler {
	private CommandHandler next;
	public CommandHandler setNext(CommandHandler next) {
		this.next = next;
        	return next;
	}
    
    public void handle(Config config) {
        if (canHandle(config)) {
            execute(config);
        } else (next != null) {
            next.handle(config);
		}
	}
    
    public abstract boolean canHandle(Config config);
    
    public abstract void execute(Config config);
}	
```

1. This code also uses the Chain of Responsibility which is a behavioral design pattern. The Chain of Responsibility 
pattern depends on encapsulating specific behaviors as separate objects, referred to as handlers. Each having 
its own class and finally as its name suggests, they are all chained together using a reference to the next part 
of the chain. This is a beneficial pattern for class decoupling thanks to using encapsulation. It is also useful 
for maintainability since it allows to easily integrate new handlers without breaking the existing code. We can see this
where the .handle method is called. This probably chains to the other handlers. 

2.

___
