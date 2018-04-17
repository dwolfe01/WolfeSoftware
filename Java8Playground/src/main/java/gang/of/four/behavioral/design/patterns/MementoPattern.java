package gang.of.four.behavioral.design.patterns;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MementoPattern {
	
	private class SmartHomeLightsOriginator {
		
		private Color lightColour = Color.BLUE;
		private Double brightness = 0.75;

		public void setLightColour(Color lightColour) {
			this.lightColour = lightColour;
		}

		public void setBrightness(Double brightness) {
			this.brightness = brightness;
		}
		
		public SmartHomeLightsMemento getMemento() {
			return new SmartHomeLightsMemento(lightColour, brightness);
		}
		
		public void restoreStateFromMemento(SmartHomeLightsMemento smartHomeLightsMemento) {
			this.lightColour = smartHomeLightsMemento.getLightColour();
			this.brightness = smartHomeLightsMemento.getBrightness();
		}

		
		public String toString() {
			return "Light colour: " + lightColour.getRGB() + " Brightness " + brightness;
		}
		
	}
	
	private class SmartHomeLightsMemento{
		private Color lightColour = Color.BLUE;
		private Double brightness = 0.75;
		private Date timestamp;
		
		public SmartHomeLightsMemento(Color lightColour, Double brightness){
			this.lightColour = lightColour;
			this.brightness = brightness;
			this.timestamp = new Date();
		}
		
		public Color getLightColour() {
			return lightColour;
		}

		public Double getBrightness() {
			return brightness;
		}

		public Date getTimestamp() {
			return timestamp;
		}
	}
	
	private class SmartHomeLightsCaretaker {
		Map<Date, SmartHomeLightsMemento> mementos = new HashMap<Date, SmartHomeLightsMemento>();
		
		public Date setMemento(SmartHomeLightsMemento memento){
			mementos.put(memento.getTimestamp(), memento);
			return memento.getTimestamp();
		}
		
		public SmartHomeLightsMemento getMemento(Date timestamp){
			return mementos.get(timestamp);
		}
	}

	public static void main(String[] args) {
		MementoPattern mp = new MementoPattern();
		SmartHomeLightsCaretaker smartHomeLightsCaretaker = mp.new SmartHomeLightsCaretaker();
		SmartHomeLightsOriginator smartHomeLightsOriginator = mp.new SmartHomeLightsOriginator();
		System.out.println(smartHomeLightsOriginator.toString());//this is the default.
		smartHomeLightsOriginator.setLightColour(Color.RED);//this is a change
		System.out.println(smartHomeLightsOriginator.toString());
		Date timestamp = smartHomeLightsCaretaker.setMemento(smartHomeLightsOriginator.getMemento());
		smartHomeLightsOriginator.setLightColour(Color.BLACK);//this is a second change
		smartHomeLightsOriginator.setBrightness(0.1);//this is a second change
		System.out.println(smartHomeLightsOriginator.toString());
		smartHomeLightsOriginator.restoreStateFromMemento(smartHomeLightsCaretaker.getMemento(timestamp));//return the state (rollback)
		System.out.println(smartHomeLightsOriginator.toString());
	}

}
