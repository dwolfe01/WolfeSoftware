public class TryCatchFinally {

    public static void main(String[] args){
        getString();
    }

    public static void getString(){
        try {
            System.out.println("throwing exception");
            if (true) throw new RuntimeException();
//        }catch (Exception e){
//            System.out.println("catch block");
//            return "test";
        }finally{
            System.out.println("finally");
            System.out.println("test2");
        }
        System.out.println("finished");
    }
}
