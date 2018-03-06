package gang.of.four.creational.design.patterns;

public class BuilderPattern {
	
	public enum RAM {
		FOUR(4), EIGHT(8), SIXTEEN(16), THIRTY_TWO(32);
		
		private int size;

		RAM(int size){
			this.size = size;
		}
		
		public int getSize() {
			return this.size;
		}
	};
	
	class Laptop{
		RAM RAM;
		String CPU;
		int screenSize;
		public Laptop() {
			
		}
		public Laptop(RAM ram, String cpu, int screenSize) {
			this.RAM = ram;
			this.CPU = cpu;
			this.screenSize = screenSize;
		}
		public String toString() {
			return "RAM: " + RAM + " CPU: " + CPU + " Screen Size: " + screenSize;
		}
	}
	
	//here is the design pattern
	class LaptopBuilder{
		private RAM ram;
		private String cpu;
		private int screenSize;

		public LaptopBuilder withRAM(RAM ram) {
			this.ram = ram;
			return this;
		}
		
		public LaptopBuilder withCPU(String cpu) {
			this.cpu = cpu;
			return this;
		}
		
		public LaptopBuilder withScreenSize(int screenSize) {
			this.screenSize = screenSize;
			return this;
		}
		
		public Laptop build() {
			return new Laptop(ram, cpu, screenSize);
		}
		
	}
	
	public static void main(String... args) {
		BuilderPattern bp = new BuilderPattern();
		System.out.println(bp.new Laptop(RAM.SIXTEEN, "Intel", 13));
		System.out.println(bp.new LaptopBuilder().withRAM(RAM.SIXTEEN).withCPU("Intel").withScreenSize(13).build());
	}

}
