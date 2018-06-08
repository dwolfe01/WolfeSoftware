package gang.of.four.behavioral.design.patterns;

import java.util.HashMap;
import java.util.Map;

public class MediatorPattern {

	class Telephone {

		private String myNumber;
		private TelephoneCallMediator telephoneCallMediator;

		public String getMyNumber() {
			return myNumber;
		}

		public Telephone(String myNumber, TelephoneCallMediator telephoneCallMediator) {
			this.myNumber = myNumber;
			this.telephoneCallMediator = telephoneCallMediator;

		}

		public void makeCall(String number,String speech) {
			telephoneCallMediator.speakTo(number, this, speech);
		}

		public void speak(String callersNumber, String speech) {
			System.out.println("*** I am:" + myNumber);
			System.out.println("Message from: " + callersNumber + ": " + speech);
		}

	}

	class TelephoneCallMediator {

		public Map<String, Telephone> telephones = new HashMap<>();

		public void register(Telephone... myTelephones) {
			for (Telephone t : myTelephones) {
				telephones.put(t.getMyNumber(), t);
			}
		}

		public void speakTo(String toNumber, Telephone telephone, String speech) {
			//ensure the calling telephone is registered with this mediator
			if (!telephones.containsValue(telephone)) {
				System.out.println("Calling telephone not registered");
				return;
			}
			Telephone toTelephone = telephones.get(toNumber);
			if (toTelephone==null) {
				System.out.println("Cannot find number");
				return;
			}
			toTelephone.speak(telephone.getMyNumber(), speech);
		}
	}

	public static void main(String... args) {
		MediatorPattern mp = new MediatorPattern();
		TelephoneCallMediator telephoneCallMediator = mp.new TelephoneCallMediator();
		Telephone telephone = mp.new Telephone("123", telephoneCallMediator);
		Telephone telephone2 = mp.new Telephone("456",telephoneCallMediator);
		Telephone telephone3 = mp.new Telephone("789",telephoneCallMediator);
		telephoneCallMediator.register(telephone,telephone2,telephone3);
		telephone.makeCall("789","Hello");
		telephone3.makeCall("456", "I love the mediator pattern");
		//unknown toNumber
		telephone2.makeCall("134", "Anybody there?");
		//unknown calling telephone
		Telephone telephone4 = mp.new Telephone("134",telephoneCallMediator);
		telephone4.makeCall("456", "Hello 456 did you want me?");
		//register the new phone
		telephoneCallMediator.register(telephone4);
		telephone2.makeCall("134", "Anybody there?");
		telephone4.makeCall("456", "Hello 456 did you want me?");
	}

}
