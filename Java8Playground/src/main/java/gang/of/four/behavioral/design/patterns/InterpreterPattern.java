package gang.of.four.behavioral.design.patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class InterpreterPattern {
	
	public static void main(String[] args) throws IOException {
		InterpreterPattern ip = new InterpreterPattern();
		//the expressions is a grammar which is interpreted into a search
		OrExpression orExpression = ip.new OrExpression(ip.new WordExpression("Romeo"), ip.new WordExpression("Juliet"));
		ip.new Interpreter(ip.new Context(),orExpression).interpret().count();
		AndExpression andExpression = ip.new AndExpression(ip.new WordExpression("Romeo"), ip.new WordExpression("Juliet"));
		ip.new Interpreter(ip.new Context(), andExpression).interpret().forEach(System.out::println);
		NotExpression notExpression = ip.new NotExpression(ip.new WordExpression("SERVICE"));
		ip.new Interpreter(ip.new Context(), notExpression).interpret().forEach(System.out::println);
	}

	abstract class Expression {
		protected Predicate<String> predicate;
		boolean interpret(String string) {
			return predicate.test(string);
		}
	}
	
	class NotExpression extends Expression {
		public NotExpression(Expression expression) {
			this.predicate = s -> !expression.interpret(s);
		}
	}

	class WordExpression extends Expression {
		public WordExpression(String word) {
			this.predicate = s -> s.contains(word);
		}
	}

	class OrExpression extends Expression {
		public OrExpression(Expression expression1, Expression expression2) {
			this.predicate = s ->expression1.interpret(s) || expression2.interpret(s);
		}
	}

	class AndExpression extends Expression {
		public AndExpression(Expression expression1, Expression expression2) {
			this.predicate = s ->expression1.interpret(s) && expression2.interpret(s);
		}
	}

	class Interpreter {
		protected Supplier<Stream<String>> myFunction;
		
		public Interpreter(Context context, Expression expression) {
			myFunction = () -> context.getLinesAsStream().filter(s -> expression.interpret(s));
		}
		
		public Stream<String> interpret() {
			return myFunction.get();
		}
	}
	
	class Context {
		Path path;
		public Context() throws IOException {
			this.path = Paths.get("src/test/resources/story.txt");
		}
		
		public Stream<String> getLinesAsStream() {
			try {
				return Files.lines(path);
			} catch (IOException e) {
				throw new RuntimeException("Not able to read lines from the file");
			}
		}
	}

	

}
