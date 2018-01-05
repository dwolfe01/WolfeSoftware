import java.util.function.Function;

import repairable.Car;

public class JavaFunctions {

	public static void main(String[] args) {
		 class MyFunction implements Function<Integer, Integer>{

			@Override
			public Integer apply(Integer t) {
				// TODO Auto-generated method stub
				return t + 1;
			}
			
			@Override
			public <V> Function<V, Integer> compose(Function<? super V, ? extends Integer> before) {
				// TODO Auto-generated method stub
				return Function.super.compose(before);
			}
			
			public Function<Integer, Integer> compose2(Function<Integer, Integer> before) {
				return x -> apply(before.apply(x));
			}
		};
		MyFunction increment = new MyFunction();
		System.out.println(increment.compose2(increment).apply(5));
		System.out.println("Completed...");

		Function<Function<Integer, Integer>, Function<Integer, Integer>> myFunction = x -> x.compose(x);
		
		
	}

}

