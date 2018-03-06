package gang.of.four.creational.design.patterns;

public class PrototypePattern {

	class ComplexObject implements Cloneable {
		private String name;
		private String complexDetailsFromThirdPartyWebsite;
		
		public String getComplexDetailsFromThirdPartyWebsite() {
			return complexDetailsFromThirdPartyWebsite;
		}

		public ComplexObject() throws InterruptedException {
			name = Math.random() + "";
			//other time consuming work here perhaps hydrating a bunch of information from an third party web service
			//these details do not change per instance (and hence are part of the prototype) from which concrete instances can be cloned (created)
			Thread.sleep(1000);
			complexDetailsFromThirdPartyWebsite = "hello third party details";
		}

		public ComplexObject clone() throws CloneNotSupportedException {
			return (ComplexObject) super.clone();
		}

		public String showDetails() {
			return name + " " + complexDetailsFromThirdPartyWebsite;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	class ComplexObjectPrototypePattern {
		private ComplexObject complexObject;

		public ComplexObjectPrototypePattern() throws InterruptedException {
			complexObject = new ComplexObject();
		}

		public ComplexObject getComplexObject() throws CloneNotSupportedException {
			ComplexObject clone = (ComplexObject) complexObject.clone();
			clone.setName(Math.random() + "");
			return clone;
		}
	}

	public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
		PrototypePattern pp = new PrototypePattern();
		System.out.println(pp.new ComplexObject().showDetails());
		System.out.println(pp.new ComplexObject().showDetails());
		System.out.println(pp.new ComplexObject().showDetails());
		ComplexObjectPrototypePattern complexObjectPrototypePattern = pp.new ComplexObjectPrototypePattern();
		System.out.println(complexObjectPrototypePattern.getComplexObject().clone().showDetails());
		System.out.println(complexObjectPrototypePattern.getComplexObject().clone().showDetails());
		System.out.println(complexObjectPrototypePattern.getComplexObject().clone().showDetails());
	}
	

}
