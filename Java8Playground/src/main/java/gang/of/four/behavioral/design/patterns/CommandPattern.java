package gang.of.four.behavioral.design.patterns;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommandPattern {
	
	//client - passes a command object to the invoker
	public static void main(String...args) {
		CommandPattern cp = new CommandPattern();
		CCTV cctv = cp.new CCTV();
		EnableCCTV startCCTV = cp.new EnableCCTV(cctv);
		Invoker invoker = cp.new Invoker();
		invoker.invoke(startCCTV);
		DisableCCTV stopCCTV = cp.new DisableCCTV(cctv);
		stopCCTV.setMilliseconds(10000);
		invoker.invoke(stopCCTV);
		invoker.shutdown();
	}
	
	//invoker - knows how to execute a command, but it does not know about recievers, it can do bookkeeping around a command
	class Invoker{
		private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10);
		public void invoke(Command command) {
			scheduledThreadPoolExecutor.schedule(command::go, command.getMilliseconds(), TimeUnit.MILLISECONDS);
		}
		public void shutdown() {
			this.scheduledThreadPoolExecutor.shutdown();
		}
	}
	
	//command object - knows about the object that will recieve the command i.e. the implementation and knows any parameters it may need
	
	abstract class Command {
		long milliseconds = 0;
		abstract public void go();
		public long getMilliseconds() {
			return milliseconds;
		}
		public void setMilliseconds(long milliseconds) {
			this.milliseconds = milliseconds;
		}
	}
	
	class EnableCCTV extends Command{
		private CCTV cctv;
		public EnableCCTV(CCTV cctv) {
			this.cctv = cctv;
		}
		public void go() {
			cctv.cctvON();
		}
	}
	
	class DisableCCTV extends Command{
		private CCTV cctv;
		public DisableCCTV(CCTV cctv) {
			this.cctv = cctv;
		}
		public void go() {
			cctv.cctvOFF();
		}
	}
	
	//reciever - actually carries out the execution of a command
	class CCTV{
		public void cctvON(){
			System.out.println("CCTV recording...");
		}
		public void cctvOFF(){
			System.out.println("CCTV stopped...");
		}
	}

}
