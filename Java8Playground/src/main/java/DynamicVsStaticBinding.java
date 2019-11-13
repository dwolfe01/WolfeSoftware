import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class DynamicVsStaticBinding {

    public static void main(String[] args) {
        Collection c = new ArrayList(){
            @Override
            public boolean add(Object o) {
                System.out.println("This is dynamic binding");
                return false;
            }
        };
        staticBinding(c);
        c.add(null);
    }

    private static void staticBinding(Collection c) {
        System.out.println("This is static binding");
    }

    private static void staticBinding(ArrayList c) {
        System.exit(1);
    }

}
