package gang.of.four.behavioral.design.patterns;

import java.util.ArrayList;
import java.util.List;

public class VisitorPattern {
	
	/* for a disprate aggregation of objects to allow a class to visit each of those objects and for the correct method to be called, with a concrete type, on the visiting object*/ 
	
	List<Visitable> list = new ArrayList<>();
	
	public void addVisitableElement(Visitable v) {
		list.add(v);
	}
	
	public void acceptVisitor(Visitor v) {
		for (Visitable visitable:list) {
			visitable.accept(v);
		}
	}
	
	public static void main(String[] args) {
		VisitorPattern vp = new VisitorPattern();
		A a = new A();
		B b = new B();
		vp.addVisitableElement(a);
		vp.addVisitableElement(b);
		vp.acceptVisitor(new ConcreteVisitor());
	}

}

interface Visitable{
	public void accept(Visitor v);
}

class A implements Visitable {
	public String getName() {
		return "A";
	}
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}

class B implements Visitable{
	public String getName() {
		return "B";
	}
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}

interface Visitor{
	public void visit(A a);
	public void visit(B a);
}

class ConcreteVisitor implements Visitor{
	public void visit(A a) {
		System.out.println("Visitor A : " + a.getName());
	}

	@Override
	public void visit(B b) {
		System.out.println("Visitor B : " + b.getName());
	}
}