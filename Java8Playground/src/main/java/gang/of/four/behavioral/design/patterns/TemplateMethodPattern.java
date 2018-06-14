package gang.of.four.behavioral.design.patterns;

public class TemplateMethodPattern {
	
	public abstract class CocktailMaker {
		
		public final void makeDrink() {
			getCup();
			addSpirits();
			addMixer();
			mix();
		}

		public abstract void mix();

		public abstract void addMixer();

		public abstract void addSpirits();

		public abstract void getCup();
		
	}
	
	public class ScrewDriver extends CocktailMaker{

		@Override
		public void mix() {
			System.out.println("Shaking...");
			
		}

		@Override
		public void addMixer() {
			System.out.println("Adding orange juice");
			
		}

		@Override
		public void addSpirits() {
			System.out.println("Adding vodka");
			
		}

		@Override
		public void getCup() {
			System.out.println("Getting highball class");
			
		}
		
	}
	
	public static void main(String... args) {
		TemplateMethodPattern templateMethodPattern = new TemplateMethodPattern();
		templateMethodPattern.new ScrewDriver().makeDrink();
	}

}
