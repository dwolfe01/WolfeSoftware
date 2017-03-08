import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PersonHandler {

	public static void outputMatchingPersons(List<Person> list,
			CheckPerson predicate) {
		for (Person p : list) {
			if (predicate.test(p)) {
				printPerson(p, person -> person.getName());
			}
		}
	}

	public static <T> void outputMatchingWithGenerics(List<T> list,
			Predicate<T> predicate, Consumer<T> consumer) {
		for (T t : list) {
			if (predicate.test(t)) {
				consumer.accept(t);
			}
		}
	}
	
	public static void printPerson(Person person, PrintPerson p) {
		System.out.println(p.printPerson(person));
	}

	public static void main(String[] args) {
		List<Person> listOfPerson = createListOfPersons();

		System.out.println("//anonymous class");
		outputMatchingPersons(listOfPerson, new CheckPerson() {

			@Override
			public boolean test(Person p) {
				return true;
			}

		});

		System.out.println("//predicate passed as argument");
		outputMatchingPersons(listOfPerson,
				person -> person.getEmailAddress() == null);

		System.out.println("// assign lambda to variable");
		Consumer<Person> consumer = person -> System.out.println(person
				.getName());
		outputMatchingWithGenerics(listOfPerson, person -> person.getName()
				.equals("Anna"), consumer);
		
		System.out.println("//now using streams rather than loops");
		listOfPerson.stream().forEach(person -> System.out.println(person.getName()));
		
		System.out.println("//now using streams with predicate");
		listOfPerson.stream().filter(person -> person.getName().equals("Anna")).forEach(person -> System.out.println(person.getName()));
		
	}

	private static List<Person> createListOfPersons() {
		List<Person> listOfPerson = new ArrayList<Person>();
		listOfPerson.add(new Person("Jon"));
		listOfPerson.add(new Person("Anna"));
		listOfPerson.add(new Person("Pete"));
		return listOfPerson;
	}
}

interface CheckPerson extends Predicate<Person> {
}

interface PrintPerson {
	String printPerson(Person p);
}
