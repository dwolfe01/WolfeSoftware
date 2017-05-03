
public class InnersAndOuters {

	private String myName = "Outer";
	public Inner inner;
	
	public class Inner {
		public void seeOuter() {
			System.out.println("FROM INNER myName :" + myName);
			printMyName();
			InnersAndOuters.this.printMyName();
		}
		
		public void printMyName(){
			System.out.println("FROM INNER printMyName myName :" + myName);
		}
	}
	
	public InnersAndOuters(){
		inner = new Inner();
	}
	
	public void printMyName(){
		System.out.println("FROM OUTER printMyName myName :" + myName);
	}
	

	public static void main(String[] args){
		InnersAndOuters innersAndOuters = new InnersAndOuters();
		innersAndOuters.inner.seeOuter();
		System.out.println("*****");
		Inner inner = innersAndOuters.new Inner();
		inner.seeOuter();
		inner = innersAndOuters.new Inner(){
			@Override
			public void seeOuter() {
				System.out.println("do nothing");
			}
		};
		inner.seeOuter();
	}
}
