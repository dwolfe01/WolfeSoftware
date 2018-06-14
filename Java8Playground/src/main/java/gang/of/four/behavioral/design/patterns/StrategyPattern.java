package gang.of.four.behavioral.design.patterns;

public class StrategyPattern {
	
	private interface Drive extends Runnable{};
	
	enum DrivingStrategy {
		FAST(() -> {
			System.out.println("Driving fast");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}),
		SLOW(() -> {
			System.out.println("Driving slow");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		
		private Drive drive;
		
		public void go() {
			drive.run();
		}

		DrivingStrategy(Drive d) {
			this.drive = d;
		}
	}

	public class SmartAutoCar{
		
		DrivingStrategy strategy = DrivingStrategy.FAST;
		
		public void setStrategy(DrivingStrategy ds) {
			strategy = ds;
		}
		
		public void go() {
			for (int x=0;x<10;x++) {
				strategy.go();
			}
		}
		
	}
	
	public static void main(String[] args) {
		StrategyPattern strategyPattern = new StrategyPattern();
		SmartAutoCar smartAutoCar = strategyPattern.new SmartAutoCar();
		smartAutoCar.go();
		smartAutoCar.setStrategy(DrivingStrategy.SLOW);
		smartAutoCar.go();
	}

}
