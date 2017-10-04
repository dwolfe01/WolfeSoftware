import java.util.concurrent.Callable;

public class CallableVsRunnable {
	
	public static void main(String[] args){
		Callable<String> callable = new Callable<String>(){
			@Override
			public String call() throws NullPointerException {
				return "Callable" + System.currentTimeMillis() + " ";
			}
		};
		try {
			System.out.println(callable.call());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//notice no return type or exceptions can be handled from a runnable
		new Runnable(){
			@Override
			public void run() {
				System.out.println("Runnable" + System.currentTimeMillis() + " ");
			}
			
		}.run();
		//what about this?
		class isThisMad<T> implements Runnable{
			
			Callable<T> callable;
			public void setCallable(Callable<T> callable){
				this.callable = callable;
			}
			@Override
			public void run() {
				try {
					System.out.println(callable.call());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		isThisMad<String> isThisMad = new isThisMad<String>();
		isThisMad.setCallable(callable);
		isThisMad.run();
	}

}
