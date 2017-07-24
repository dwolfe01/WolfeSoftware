
public class Compatibility {

	public static void main(String[] args) {
		Integer myShort = 1;
		//this is implicitly compatible
		Number myInt = myShort;
		//type mismatch cannot convert from int to short because they are not compatible
		//myShort = myInt;
		//must use a cast this is explicitly compatible.
		myShort = (Integer) myInt;
		//Generally speaking, we can say that array types are covariant in Java (because there compatibility depends on their generic types)
		Integer[] myIntArray = {1,2,3};
		Number[] myNumberArray = myIntArray;//this means that Number[] is implicitly covariant to  Integer[]
		myIntArray = (Integer[]) myNumberArray;//this means that Number[] is explicitly covariant to  Integer[]
	}

}
