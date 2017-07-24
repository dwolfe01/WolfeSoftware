package repairable;

import java.util.Arrays;
import java.util.List;

public class RepairShop {

	public static void main(String[] args) {
		//fixAll(Arrays.asList(new Computer(), new Car()));
		fixAllCars(Arrays.asList(new SuperCar(), new Car()));
	}
	
	public static void fixAll(List<Repairable> repairables){
		repairables.forEach(rep -> rep.fix());
	}
	
	public static void fixAllCars(List<Car> repairables){
		repairables.forEach(rep -> rep.fix());
	}

}
