package gang.of.four.structural.design.patterns;

public class DecoratorPattern {
	
	class DecorateSimpleText{
		private SimpleText text;

		public DecorateSimpleText(SimpleText text) {
			this.text = text;
			
		}

		public String get() {
			return "<h1>" + text.get()+"</h1>";
		}
	}
	
	class SimpleText{
		String contents = "Hello World";

		public String get() {
			return contents;
		}
	}
	
	public static void main(String...args) {
		DecoratorPattern dp = new DecoratorPattern();
		SimpleText text = dp.new SimpleText();
		System.out.println(text.get());
		System.out.println(dp.new DecorateSimpleText(text).get());
	}
	
}
