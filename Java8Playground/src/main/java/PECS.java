import java.util.ArrayList;
import java.util.List;

//Remember PECS: "Producer Extends (only reads), Consumer Super (only puts)"
public class PECS {
	

    public class A {
    	public String getName(){
    		return "A";
    	}
    }

    public class B extends A {
    	public String getName(){
    		return "B";
    	}
    }

    public class C extends B {
    	public String getName(){
    		return "C";
    	}
    }

    public void testCoVariance(List<? extends B> myBlist) {
        B b = new B();
        C c = new C();
        //myBlist.add(b); // does not compile
        //myBlist.add(c); // does not compile
        B a = myBlist.get(0); 
    }

    public void testContraVariance(List<? super B> myBlist) {
        B b = new B();
        C c = new C();
        myBlist.add(b);
        myBlist.add(c);
        //A a = myBlist.get(0); // does not compile
    }
    
    public static void main(String[] args){
    	PECS pecs = new PECS();
    	//polymoprhism
    	List<A> myList = new ArrayList<A>();
    	myList.add(pecs.new A());
    	myList.add(pecs.new B());
    	myList.add(pecs.new C());
    	pecs.simplePrint(myList);
    	//covariance / Produce extends
    	List<B> myListCOVARIANT = new ArrayList<B>();
    	myListCOVARIANT.add(pecs.new B());
    	myListCOVARIANT.add(pecs.new C());
    	pecs.simplePrint2(myListCOVARIANT);
    	pecs.simplePrint2(myList);
    	//contravariance / Consumer super
    	List<A> myListContravariant = new ArrayList<A>();
    	pecs.addStuff(myListContravariant);
    	pecs.simplePrint2(myListContravariant);
    }
    
    private void addStuff(List<? super B> myListContravariant) {
		myListContravariant.add(new C());
		myListContravariant.add(new B());
	}
    
    //covariance
    public void simplePrint2(List<? extends A> list){
    	for (A a:list){
    		System.out.println(a.getName());
    	}
    }
    
    //polymorphism
    public void simplePrint(List<A> list){
    	for (A a:list){
    		System.out.println(a.getName());
    	}
    }
}
	

