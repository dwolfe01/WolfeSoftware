package gang.of.four.behavioral.design.patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class InterpreterPattern {

	class Context {
		Path path;
		public Context() throws IOException {
			this.path = Paths.get("src/test/resources/story.txt");
		}
		
		public Stream<String> getLinesAsStream() throws IOException {
			return Files.lines(path);
		}
	}

	abstract class Expression {
		abstract boolean interpret(String string);
	}
	
	class NotExpression extends Expression {
		private Expression expression;

		public NotExpression(Expression expression) {
			this.expression = expression;
		}

		boolean interpret(String str) {
			return !this.expression.interpret(str);
		}
		
	}

	class WordExpression extends Expression {
		private String word;

		public WordExpression(String word) {
			this.word = word;
		}

		public boolean interpret(String wordFromContext) {
			return wordFromContext.contains(word);
		}
	}

	class OrExpression extends Expression {
		private Expression expression1 = null;
		private Expression expression2 = null;

		public OrExpression(Expression expression1, Expression expression2) {
			this.expression1 = expression1;
			this.expression2 = expression2;
		}

		public boolean interpret(String str) {
			return expression1.interpret(str) || expression2.interpret(str);
		}
	}

	class AndExpression extends Expression {

		private Expression expression1;
		private Expression expression2;

		public AndExpression(Expression expression1, Expression expression2) {
			this.expression1 = expression1;
			this.expression2 = expression2;
		}

		public boolean interpret(String str) {
			return expression1.interpret(str) && expression2.interpret(str);
		}
	}

	class Interpreter {
		private Context context;
		private Expression expression;

		public Interpreter(Context context, Expression expression) {
			this.context = context;
			this.expression = expression;
		}
		
		public Stream<String> interpret() throws IOException {
			//return context.getLinesAsStream();
			return context.getLinesAsStream().filter(s -> expression.interpret(s));
		}
	}

	public static void main(String[] args) throws IOException {
		InterpreterPattern ip = new InterpreterPattern();
		//the expressions is a grammar which is interpreted into a search
		OrExpression orExpression = ip.new OrExpression(ip.new WordExpression("Romeo"), ip.new WordExpression("Juliet"));
		ip.new Interpreter(ip.new Context(),orExpression).interpret().count();
		AndExpression andExpression = ip.new AndExpression(ip.new WordExpression("Romeo"), ip.new WordExpression("Juliet"));
		ip.new Interpreter(ip.new Context(), andExpression).interpret().forEach(System.out::println);;
		NotExpression notExpression = ip.new NotExpression(ip.new WordExpression("Romeo"));
		ip.new Interpreter(ip.new Context(), notExpression).interpret().forEach(System.out::println);;
	}

}
