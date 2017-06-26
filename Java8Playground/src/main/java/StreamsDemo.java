import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamsDemo {
	
	private static Stream<String> sorted;

	public static void main(String[] args){
		Stream.of(1,2,3,4).forEach(System.out::println);
		
		Stream.iterate(1, val -> ++val).limit(10).forEach(System.out::println);
		
		
		//Lazy streams
		Optional<Integer> findFirst = Stream.of(1,2,3,4).map(i -> i=doSomethingWithThis(i)).filter(i -> i==3).findFirst(); //note here that doSomethingWithThis is only called twice
		System.out.println(findFirst);

		//Lazy streams
		findFirst = Stream.of(1,2,3,4).map(i -> --i).findFirst();
		System.out.println(findFirst);
		
		
		
		//sorting
		List<String> asList = Arrays.asList("this", "is", "a", "sorted", "list");
		asList.stream().sorted(StreamsDemo::sortForward).forEach(System.out::println);
		asList.stream().sorted(StreamsDemo::sortBackward).forEach(System.out::println);
		System.out.println("Finished!");

	}
	
	private static int sortForward(String s1, String s2){
		if (s1.length()>s2.length()){
			return -1;
		}
		return 1;
	}

	private static int sortBackward(String s1, String s2){
		if (s2.length()>s1.length()){
			return -1;
		}
		return 1;
	}

	private static int doSomethingWithThis(Integer i) {
		System.out.println("doSomethingWithThis" + i);
		i++;
		return i;
	}
	
}
