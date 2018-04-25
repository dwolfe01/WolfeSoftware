package gang.of.four.structural.design.patterns;

public class ProxyPattern {

	public static ClassToProxy getProxyClass() {
		return new ClassToProxy() {
			public void doMethodA() {
				System.out.println("Doing access check");
				super.doMethodA();
			}

			public void doMethodB() {
				System.out.println("Doing access check");
				super.doMethodB();
			}

		};
	}
	
	public static void main(String...args) {
		ClassToProxy concreteClass = new ClassToProxy();
		concreteClass.doMethodA();
		concreteClass.doMethodB();
		ClassToProxy proxyClass = getProxyClass();
		proxyClass.doMethodA();
		proxyClass.doMethodB();
	}
}
	

class ClassToProxy{
	public void doMethodA() {
		System.out.println("This is concrete class methodA");
	}
	public void doMethodB() {
		System.out.println("This is concrete class methodB");
	}
}