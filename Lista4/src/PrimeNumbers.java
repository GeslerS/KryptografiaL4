import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
public class PrimeNumbers {

	public static void main(String[] args) {
		
		if(args.length != 2){
			System.out.println("Incorrect number of arguments");
			return;
		}
		int d = Integer.parseInt(args[0]); //Liczba liczba bitów
		int k = Integer.parseInt(args[1]); //Liczba wątków
		
		
		SecureRandom random = new SecureRandom();
		
		Runnable runnable[] = new Runnable[d];
		Thread thread[] = new Thread[d];
		
		Calendar calendar = new GregorianCalendar();
		for(int i = 0; i < d; i++){
			runnable[i] = new PrimaryThread(k,i,random);
			thread[i] = new Thread(runnable[i]);
			thread[i].start();
		}
		

	}

}