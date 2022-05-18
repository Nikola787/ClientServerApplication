package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

//klasa Client implementira "Runnable", kako bi mogla da se pokrene kao nit
public class Client implements Runnable{
	
	//deklaracija promenljivih
	static Socket soketZaKomunikaciju = null;
	//ulaz-izlaz za soket
	static BufferedReader serverInput = null;
	static PrintStream serverOutput = null;
	//unos sa tastature
	static BufferedReader unosSaTastature = null;
	
	public static void main(String[] args) {
		
		try {
			
            // Kada pokrenemo klijenta gadjamo localhost i port 9002 def. na serverskoj strani
			soketZaKomunikaciju = new Socket("localhost", 9002);
			
			//inicijalizacija ulazni tok, sluza za primanje poruke od servera
			serverInput = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream())); 
			//inicijalizacija izlazni tok, sluzi za slanje poruka serveru
			serverOutput = new PrintStream(soketZaKomunikaciju.getOutputStream()); 
			
			//inicijalizacija unosa sa tastature
			unosSaTastature = new BufferedReader(new InputStreamReader(System.in));
			
			//pravimo nit koja ce da cita poruke, odnosno pokrecemo metodu Run
			new Thread(new Client()).start();
			
            // Dokle god stizu poruke, iste se ispisuju na strani klijenta.
            // Ako dodje poruka koja pocinje sa Uspesno ste izasli, a to je u slucaju da smo mi uneli esc, zatvara se
            // soket za komunikaciju
			
			String input; //poruke od servera koje treba da se prikazu
			
			//dokle god nije kraj prikazivace poruke od servera klijentu
			while(true) {
				
				input = serverInput.readLine();
				System.out.println(input);
				
				//kada se korisniku ispise uspesno ste izasli prekida se while petlja
				if(input.startsWith("Uspesno ste izasli")) {
					break;
				}
				
			}
			//zatvaramo soket
			soketZaKomunikaciju.close();
			
	        // Obradjena su dva izuzetka:
            // Prvi u slucaju da je nepoznat host tj. server na koji se kacimo
            // Drugi u slucaju da server iznenada prestane sa radom npr.	
			
		} catch (UnknownHostException e) {
			
			System.out.println("Nepoznat host");
		
		} catch (IOException e) {
			
			System.out.println("Server je pao!");
			
		}
	}
	
    // U okviru RUN metode saljemo poruke koje klijent otkuca ka serveru
	@Override
	public void run() {
		
		String message = null;
		
		while(true) {
			
			try {
			//citamo unos koji korisnik unese
				message = unosSaTastature.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			serverOutput.println(message);
			
			//kada korisnik unese esc to oznacava da zeli da izadje iz programa pa zato i prekidamo izvrsavanje niti
			if(message.equals("-1"))
				break;
			
		}
		
	} 

}
