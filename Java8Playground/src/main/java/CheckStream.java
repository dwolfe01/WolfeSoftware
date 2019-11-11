import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CheckStream {


    public static void main(String[] args) {
        CheckStream checkStream = new CheckStream();
        Optional<String> s;
        s = Optional.of("123,4556,675,567,dan1");
        System.out.println(checkStream.isBackOrderRestrictedFamily2(s, "dan1"));
    }


    private boolean isBackOrderRestrictedFamily(Optional<String> listOfStrings, String test) {
        Predicate<Stream> matchProductFamily = list -> list.anyMatch(family -> family.equals(test));
        return listOfStrings.map(value -> Stream.of(value.split(",")))
                .filter(matchProductFamily).isPresent();
    }

    private boolean isBackOrderRestrictedFamily2(Optional<String> listOfStrings, String test) {
        return Arrays.asList(listOfStrings.get().split(",")).contains(test);
    }


}
