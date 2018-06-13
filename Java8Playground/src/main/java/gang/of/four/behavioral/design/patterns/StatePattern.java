package gang.of.four.behavioral.design.patterns;

import java.util.stream.IntStream;

public class StatePattern {

	private interface Worker {
		void doWork(int work);
	}

	public class Coordinator {

		private Worker w;

		public Coordinator() {
			this.w = this::nonSamplingThread;
		}

		public void go() {
			IntStream.range(0, 100).forEach(s -> w.doWork(s));
		}

		public void nonSamplingThread(int work) {
			System.out.println("Doing my work: " + work);
			if ((work % 10) == 0) {
				this.w = this::samplingThread;
			}
		}

		public void samplingThread(int work) {
			System.out.println("Doing my work and storing result in log file: " + work);
			this.w = this::nonSamplingThread;
		}

	}

	public static void main(String... args) {
		StatePattern statePattern = new StatePattern();
		Coordinator coordinator = statePattern.new Coordinator();
		coordinator.go();
	}

}
