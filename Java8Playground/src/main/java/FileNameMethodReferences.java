import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class FileNameMethodReferences {
	
	public static void main(String[] args){
		FileNameMethodReferences fileNameMethodReferences = new FileNameMethodReferences();
		fileNameMethodReferences.go();
		File f  = new File("src/main/java");
		//reference to an instance method of a particular object
		Arrays.stream(f.list(fileNameMethodReferences::filterFiles)).forEach(System.out::println);
	}
	
	public boolean filterFiles(File f, String name){
		return name.startsWith("P");
	}

	private void go() {
		File f  = new File("src/main/java");
		//reference to an instance method of a particular object
		Arrays.stream(f.list(this::filterFiles)).forEach(System.out::println);
		//straightforward lambda
		Arrays.stream(f.list((file,name)-> name.startsWith("P"))).forEach(System.out::println);
	}

}

