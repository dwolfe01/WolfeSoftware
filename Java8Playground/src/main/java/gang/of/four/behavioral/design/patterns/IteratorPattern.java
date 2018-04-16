package gang.of.four.behavioral.design.patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IteratorPattern{
	
	public static void main(String... args) throws IOException {
		IteratorPattern iteratorPattern = new IteratorPattern();
		getStream(iteratorPattern::iterator).forEach(System.out::println);
		getStream(iteratorPattern::sortedIterator).forEach(System.out::println);
		getStream(iteratorPattern::reverseIterator).forEach(System.out::println);
	}
	
	private String[] lines;

	public IteratorPattern() throws IOException{
		lines = Files.lines(Paths.get("src/test/resources/story.txt")).toArray(String[]::new);
	}
	
	private static Stream<String> getStream(Supplier<Iterator<String>> iteratorSupplier) {
		return StreamSupport.stream(
			    Spliterators.spliterator(iteratorSupplier.get(), 0L, Spliterator.NONNULL), true);
	}

	public Iterator<String> iterator() {
		return Stream.of(lines).iterator();
	}
	
	public Iterator<String> sortedIterator() {
		return Stream.of(lines).sorted().iterator();
	}

	public Iterator<String> reverseIterator() {
		return new Iterator<String>() {
			
			int x = lines.length;

			@Override
			public boolean hasNext() {
				return x>0;
			}

			@Override
			public String next() {
				return lines[--x];
			}
			
		};
	}

}
