import java.util.Optional;

public class OptionalPlayground {

	public static void main(String[] args) {
		Person dan = new Person("Dan");
		dan.setEmailAddress("dwolfe01@hotmail.co.uk");
		Optional<Person> optionalPerson = Optional.of(dan);
		System.out.println(optionalPerson.get().getName());

		optionalPerson.ifPresent(person -> System.out.println(person.getName()));
		optionalPerson.ifPresent(System.out::println);
		optionalPerson.ifPresent(new DoSomethingWithPerson()::doTheNeedful);

		Optional<Person> optionalPerson2 = Optional.ofNullable(null);
		if (!optionalPerson2.isPresent()) {
			System.out.println("Not present");
		}

	}

}

class DoSomethingWithPerson {

	public void doTheNeedful(Person p) {
		System.out.println(p.getEmailAddress());
	}

}
