package gang.of.four.structural.design.patterns;

class FacadePattern {
	
	class Facade{
		public void accessSubSystem1(){
			SubSystem1.subSystem();
		}
		public void accessSubSystem2() {
			SubSystem2.subSystem();
		}
	}
	
	public static void main(String...args) {
		FacadePattern fp = new FacadePattern();
		Facade facade = fp.new Facade();
		facade.accessSubSystem1();
		facade.accessSubSystem2();
	}
}

class SubSystem1{
	static void subSystem() {
		System.out.println("subSystem1");
	}
}

class SubSystem2{
	static void subSystem() {
		System.out.println("subSystem2");
	}
}