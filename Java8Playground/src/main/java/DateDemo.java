import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateDemo {
	
	public static void main(String[] args){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z");
		ZonedDateTime dateTime = ZonedDateTime.parse("26/Sep/2017:04:59:59 -0400", formatter);
		System.out.println(ZonedDateTime.ofInstant(dateTime.toInstant(),ZoneId.of("BST")));
	}
}
