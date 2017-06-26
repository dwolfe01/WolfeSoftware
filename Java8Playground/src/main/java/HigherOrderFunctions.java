import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;

public class HigherOrderFunctions {

	//please note see - java.util.function - https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
	
	public static void main(String[] args) throws IOException {
		Function<String, String> decorateMyString = decorateMyString(getMyString());
		System.out.println(decorateMyString.apply(" this is a function that returns a function"));
		Path path = Paths.get("/usr/share/dict/words");
		Files.lines(path).filter((s) -> s.length()>10).forEach(System.out::println);
	}
	
	public static Function<String,String> decorateMyString(Supplier<String> passedinFunc){
		return (s1) -> passedinFunc.get() + s1;
	}
	
	public static Supplier<String> getMyString(){
		return () -> "hello function";
	}
	
	
	

}
