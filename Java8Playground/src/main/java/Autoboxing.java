import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Autoboxing {

    public static void main(String[] args) {
        String one = "true";
        Boolean b1 = Boolean.valueOf(one);  // line n1
        System.out.println("boolean b1:" + b1);
        Integer i1 = new Integer(one);
        Integer i2 = 1;
        if (b1) {
            System.out.print(i1 == i2);
        }
    }

}
