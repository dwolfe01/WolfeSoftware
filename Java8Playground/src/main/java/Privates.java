class MyPrivates {

    private String myString = "Base class - private method";

    private void print() {
        System.out.println(myString);
    }

    public static void main(String[] args) {
        new MyPrivates().print();
    }

}

public class Privates extends MyPrivates {
    private String myString = "Sub class - private method";

    private void print() {
        System.out.println(myString);
        new MyPrivatesInner().print();
    }

    public static void main(String[] args) {
        new Privates().print();
    }

    private class MyPrivatesInner {
        private void print() {
            System.out.println(myString);
        }
    }

}
