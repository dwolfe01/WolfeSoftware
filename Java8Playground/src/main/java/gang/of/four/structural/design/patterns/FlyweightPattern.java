package gang.of.four.structural.design.patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlyweightPattern {
	
	private static Path path = Paths.get("src/test/resources/story.txt");
	private static List<MyString> stringsNoFlyWeight = new ArrayList<>();
	private static List<MyString> stringsFlyWeight = new ArrayList<>();
	
	static class MyString {
		String s;
		private static final Map<String, MyString> internalListOfStrings = new HashMap<String, MyString>(); 
		
		public MyString(String s) {
			this.s = s;
		}
		
		public static MyString getMyStringFlyWeight(String s) {
			if (internalListOfStrings.containsKey(s)) {
				return internalListOfStrings.get(s);
			}
			else {
				MyString myString = new MyString(s);
				internalListOfStrings.put(s, myString);
				return myString;
			}
		}
		public String toString() {
			return s;
		}
	}
	
	public static void main(String...args) throws IOException {
		//No flyweight - big object graph
		Files.lines(path).map(s -> s.split("\\s")).flatMap(Arrays::stream).forEach(s -> stringsNoFlyWeight.add(new MyString(s)));
		System.out.println("Number of strings in this datastructure:" + stringsNoFlyWeight.stream().distinct().count());
		//stringsNoFlyWeight.stream().forEach(System.out::println);
		//Flyweight - smaller object graph
		Files.lines(path).map(s -> s.split("\\s")).flatMap(Arrays::stream).forEach(s -> stringsFlyWeight.add(MyString.getMyStringFlyWeight(s)));
		System.out.println("Number of strings in this datastructure:" + stringsFlyWeight.stream().distinct().count());
		//stringsFlyWeight.stream().forEach(System.out::println);
	}
	

}
