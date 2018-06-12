package gang.of.four.behavioral.design.patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChainOfResponsibilityPattern {
	
	private class Chain{
		
		private Handler[] handlers;

		Chain(Handler... handlers){
			this.handlers = handlers;
		}
		
		public void go(String word) {
			for (Handler h: handlers) {
				if (h.handle(word)) {
					break;
				}
			}
		}
	}
	

	private interface Handler{
		boolean handle(String request);
	}
	
	public static void main(String[] args) {
		ChainOfResponsibilityPattern pattern = new ChainOfResponsibilityPattern();
		Chain chain = pattern.new Chain(pattern::knownWord, pattern::unknownWord);
		chain.go("excitable");
		chain.go("excitabled");
	}
	
	public boolean unknownWord(String word) {
		System.out.println(word + " is not a word");
		return true;
	}
	
	public boolean knownWord(String word) {
		Path path = Paths.get("/usr/share/dict/words");
		try {
			boolean match = Files.lines(path).anyMatch(s -> s.equals(word));
			if (match) System.out.println(word + " is a word");
			return match;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
