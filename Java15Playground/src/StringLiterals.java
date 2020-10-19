public class StringLiterals {
    final static String HELLO_WORLD = "Hello JDK 15";
    final String literal = """
             HELLO_WORLD ///s /a /ppppp 23/09/ \
             Lorem ipsum dolor sit amet,
             consectetur adipiscing elit,
             sed do eiusmod tempor incididunt
             """;

    public static void main(String[] args){
        new StringLiterals().go();
    }

    private void go() {
        System.out.println(literal);
    }

}
