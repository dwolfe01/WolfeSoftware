public class TwoVariablesOneMethod {

    public static void main(String[] args){
        new TwoVariablesOneMethod().go();
    }

    private void go() {
        int globalToMethodScope = 0;
        {
            int scopedByCurlyBraces = 3;
            globalToMethodScope += scopedByCurlyBraces;
        }
        {
            int scopedByCurlyBraces = 6;
            globalToMethodScope += scopedByCurlyBraces;
        }
        System.out.println(globalToMethodScope);

    }

}
