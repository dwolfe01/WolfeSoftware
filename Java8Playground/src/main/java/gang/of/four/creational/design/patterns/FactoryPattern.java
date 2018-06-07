package gang.of.four.creational.design.patterns;

public class FactoryPattern {

	public enum LANGUAGE_FACTORY {
		FRENCH(new FrenchTranslate()), DUTCH(new DutchTranslate());

		Translator t;

		LANGUAGE_FACTORY(Translator t) {
			this.t = t;
		}

		Translator getTranslator() {
			return t;
		}
	}

	private interface Translator {
		public String translate(String text);
	}

	private static class DutchTranslate implements Translator {

		@Override
		public String translate(String text) {
			return "this is a dutch translation: " + text;
		}

	}

	private static class FrenchTranslate implements Translator {

		@Override
		public String translate(String text) {
			return "this is a french translation: " + text;
		}

	}
	
	public static void main(String... args) {
		System.out.println(FactoryPattern.LANGUAGE_FACTORY.DUTCH.getTranslator().translate("text"));
		System.out.println(FactoryPattern.LANGUAGE_FACTORY.FRENCH.getTranslator().translate("text"));
	}

}
