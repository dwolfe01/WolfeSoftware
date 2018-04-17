package gang.of.four.behavioral.design.patterns;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {

	public static void main(String[] args) {
		ObserverPattern op = new ObserverPattern();
		WeatherSubject ws = op.new WeatherSubject();
		EnglishObserver englishObserver = op.new EnglishObserver(ws);
		FrenchObserver frenchObserver = op.new FrenchObserver(ws);
		ws.addObserver(englishObserver);
		ws.addObserver(frenchObserver);
		ws.updateTemperature(33);
		ws.removeObserver(englishObserver);
		ws.updateTemperature(12);
	}
	
	private abstract class Observer{
		protected WeatherSubject weathersubject;
		public Observer(WeatherSubject weatherSubject) {
			weathersubject = weatherSubject;
		}
		
		public void update() {}
	}
	
	private class EnglishObserver extends Observer{
		
		public EnglishObserver(WeatherSubject weatherSubject) {
			super(weatherSubject);
		}
		
		public void update() {
			System.out.println("The temperature is " + " " + weathersubject.getDegreesCelsius() );
		}

	}
	
	private class FrenchObserver extends Observer{
		
		public FrenchObserver(WeatherSubject weatherSubject) {
			super(weatherSubject);
		}
		
		public void update() {
			System.out.println("la température est de " + " " + weathersubject.getDegreesCelsius() );
		}
	}
	
	
	private class WeatherSubject{
		
		List<Observer> observers = new ArrayList<Observer>();
		
		private int degreesCelsius = 0;
		
		public int getDegreesCelsius() {
			return degreesCelsius;
		}

		public void addObserver(Observer o) {
			observers.add(o);
		}
		
		public void removeObserver(Observer o) {
			observers.remove(o);
		}
		
		public void updateTemperature(int degreesCelsius) {
			this.degreesCelsius = degreesCelsius;
			this.tellAllObservers();
		}

		private void tellAllObservers() {
			observers.stream().forEach(o -> o.update());
		}
		
	}

}
