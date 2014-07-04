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

	public static void printPerson(Person person, PrintPerson p) {
		System.out.println(p.printPerson(person));
	}

	public static <T> void outputMatchingWithGenerics(List<T> list,
			Predicate<T> predicate, Consumer<T> consumer) {
		for (T t : list) {
			if (predicate.test(t)) {
				consumer.accept(t);
			}
		}
	}

	public static void main(String[] args) {
		List<Person> listOfPerson = new ArrayList<Person>();
		listOfPerson.add(new Person("Jon"));
		listOfPerson.add(new Person("Anna"));
		listOfPerson.add(new Person("Pete"));

		outputMatchingPersons(listOfPerson, new CheckPerson() {

			@Override
			public boolean test(Person p) {
				return true;
			}

		});

		outputMatchingPersons(listOfPerson,
				person -> person.getEmailAddress() == null);

		// assign lambda to variable
		Consumer<Person> consumer = person -> System.out.println(person
				.getName());
		outputMatchingWithGenerics(listOfPerson, person -> person.getName()
				.equals("Anna"), consumer);
	}
}

class Person {

	public enum Sex {
		MALE, FEMALE
	}

	private String name;
	private LocalDate birthday;
	private Sex gender;
	private String emailAddress;

	public Person(String string) {
		this.name = string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}

interface CheckPerson extends Predicate<Person> {
}

interface PrintPerson {
	String printPerson(Person p);
}
