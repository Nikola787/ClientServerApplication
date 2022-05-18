package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
	
	public static List<KlijentPodaci> klijenti = new LinkedList<>();

	public static void main(String[] args) {
		
		int port = 9002;
		ServerSocket serverSoket = null;
		Socket soketZaKomunikaciju = null;
		
		//citamo podatke iz fajla
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader("korisnici.txt"));
			boolean kraj = false;
			
			while(!kraj) {
				String pom = in.readLine();
				if(pom == null)
					kraj = true;
				else {
					KlijentPodaci kp = new KlijentPodaci();
					String[] podaci = pom.split(" ");
					kp.korisnickoIme=podaci[0];
					kp.sifra=podaci[1];
					kp.ime=podaci[2];
					kp.prezime=podaci[3];
					kp.JMBG=podaci[4];
					kp.pol=podaci[5];
					kp.email=podaci[6];
					kp.prvaDoza=Boolean.parseBoolean(podaci[7]);
					kp.drugaDoza=Boolean.parseBoolean(podaci[8]);
					kp.trecaDoza=Boolean.parseBoolean(podaci[9]);
					kp.prvaDozaNaziv=podaci[10];
					kp.drugaDozaNaziv=podaci[11];
					kp.trecaDozaNaziv=podaci[12];
					kp.kovidPropusnicaValidna=Boolean.parseBoolean(podaci[13]);
					kp.datumPrveVakcinacije = podaci[14];
					kp.datumDrugeVakcinacije = podaci[15];
					kp.datumTreceVakcinacije = podaci[16];
										
					klijenti.add(kp);
				}
			}
			
		} catch(Exception e) {
			
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			serverSoket = new ServerSocket(port);
			
			// Prihvatamo nove korisnike/klijente i za svakog pokrecemo novu NIT ClientHandler
            // koja preuzima dalju komunikciju sa njima.
            // Kao parametar prosledjujemo svakoj niti soketZaKomunikaciju
			while(true) {
				
				System.out.println("Cekam na konekciju...");
				soketZaKomunikaciju = serverSoket.accept(); //prihvatamo novu konekciju
				System.out.println("Doslo je do konekcije!");
				
				ClientHandler klijent = new ClientHandler(soketZaKomunikaciju);
				
			//pokrecemo nit
				klijent.start();
				
			}
			
		} catch (IOException e) {
			System.out.println("Greska prilikom pokretanja servera!");
		}
		
	}

}
