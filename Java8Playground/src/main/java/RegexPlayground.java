import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPlayground {

	private String regex;

	public RegexPlayground(String regex) {
		this.regex = regex;
	}

	public static void main(String[] args) {
		RegexPlayground regexPlayground = new RegexPlayground("qaf\\.onlinelibrary\\.wiley\\.com");

		boolean shouldMatch = regexPlayground.shouldMatch("qaf.onlinelibrary.wiley.com");
		if (!shouldMatch) {
			System.out.println("FAIL 1");
		}
		boolean shouldNotMatch = regexPlayground.shouldNotMatch("secure.qaf.onlinelibrary.wiley.com");
		if (!shouldNotMatch) {
			System.out.println("FAIL 2");
		}
	}

	private boolean shouldMatch(String string) {
		//return string.matches(regex);
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(string);
		return m.find();
	}

	private boolean shouldNotMatch(String string) {
		//return !string.matches(regex);
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(string);
		return !m.find();
	}
}