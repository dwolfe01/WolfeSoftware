package gang.of.four.structural.design.patterns;

public class AdapterPattern {

	interface Ship {
		public void sail();
	}

	interface Car {
		public void drive();
	}

	interface Transporter {
		public void transport();
	}

	/*
	 * This is an adapter because it allows some of type Ship to be treated as type
	 * Transporter, i.e. it adapts Ship to Transporter
	 */
	class ShipAdapter implements Transporter {
		private Ship ship;

		public ShipAdapter(Ship ship) {
			this.ship = ship;
		}

		@Override
		public void transport() {
			this.ship.sail();
		}

	}

	class CarAdapter implements Transporter {
		private Car car;

		public CarAdapter(Car car) {
			this.car = car;
		}

		@Override
		public void transport() {
			this.car.drive();
		}
	}

	public static void main(String[] args) {
		Ship ship = new Ship() {
			@Override
			public void sail() {
				System.out.println("sailing...");
			}
		};
		Car car = new Car() {
			@Override
			public void drive() {
				System.out.println("driving...");
				
			}
		};
		AdapterPattern ap = new AdapterPattern();
		//so i have a car and a ship now create a list of Transporters
		Transporter[] transporters = new Transporter[2];
		transporters[0] = ap.new ShipAdapter(ship);
		transporters[1] = ap.new CarAdapter(car);
		for (Transporter transporter: transporters) {
			transporter.transport();
		}
	}

}
