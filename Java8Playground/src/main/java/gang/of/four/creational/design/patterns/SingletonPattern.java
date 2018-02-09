package gang.of.four.creational.design.patterns;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.management.RuntimeErrorException;

public class SingletonPattern implements Serializable {

	private static final long serialVersionUID = 1L;
	private static SingletonPattern singleton = new SingletonPattern();
	private static SingletonPattern singleton2;
	
	public enum TrueSingleton{
		INSTANCE;
		public void method1() {}
	}

	// can be called many times by the main method in this class, and therefore is
	// not truly singleton
	private SingletonPattern() {
	};

	public static SingletonPattern getInstance() {
		return singleton;
	}

	public synchronized static SingletonPattern getInstanceofSingletonWithLazyInitilisation()
			throws InterruptedException {
		if (singleton2 != null) {
			return singleton2;
		} else {
			Thread.sleep(500);// sleep to try to force a multithreaded race condition
			singleton2 = new SingletonPattern();
			return singleton2;
		}
	}

	public static SingletonPattern getInstanceofSingletonWithLazyInitilisationDoubleCheck()
			throws InterruptedException {
		if (singleton2 == null) {
			synchronized (SingletonPattern.class) {//note this code block is synchronized with synchronized static"getInstanceofSingletonWithLazyInitilisation" because they block on the same object
				Thread.sleep(500);// sleep to try to force a multithreaded race condition
				if (singleton2 == null) {
					singleton2 = new SingletonPattern();
				}
			}
		}
		return singleton2;
	}
	
	//because this implements serializable then many instances can be created if they are read from disk. (Each read creates a new object)
	protected Object readResolve() {
        return singleton;
    }

	public static void main(String... args) throws Exception {
		//test 1
		SingletonPattern testSingleton1 = SingletonPattern.getInstance();
		SingletonPattern testSingleton2 = SingletonPattern.getInstance();
		if (!(testSingleton1 == testSingleton2)) {
			throw new RuntimeErrorException(null, "1. These are NOT the same instance, singleton pattern has failed");
		}
		//test 2
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<SingletonPattern> futureSingleton = executor
				.submit(() -> SingletonPattern.getInstanceofSingletonWithLazyInitilisation());
		Future<SingletonPattern> futureSingleton2 = executor
				.submit(() -> SingletonPattern.getInstanceofSingletonWithLazyInitilisationDoubleCheck());
		if (!(futureSingleton.get() == futureSingleton2.get())) {
			throw new RuntimeErrorException(null, "2. These are NOT the same instance, singleton pattern has failed");
		}
		executor.shutdown();
		//break singleton using reflection
		Constructor<? extends SingletonPattern> constructor = testSingleton1.getClass().getDeclaredConstructor(new Class[0]);
		constructor.setAccessible(true);
		SingletonPattern singletonHACKofSingleton1 = (SingletonPattern) constructor.newInstance();
		if (singletonHACKofSingleton1!=testSingleton1) {
			throw new RuntimeErrorException(null, "3. We broke singleton pattern using reflection");
		}
	}

}