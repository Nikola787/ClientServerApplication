package main;

//ovo je serverska NIT

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

	//U ovom slucaju NIT smo realizovali nasledjivanjem klase THREAD

public class ClientHandler extends Thread{	
	
	BufferedReader clientInput = null;
	PrintStream clientOutput = null;
	Socket soketZaKomunikaciju = null;
	int izbor;

    // Konstruktor za NIT
	public ClientHandler(Socket soket) {
		
		soketZaKomunikaciju = soket;
	}
	
	public KlijentPodaci registracijaKorisnika() {

		KlijentPodaci klijentP = new KlijentPodaci();
		boolean pogresanUnos = true;
		
		String korisnickoIme = null;
		while(pogresanUnos) {
		clientOutput.println("(Unosom \"esc\" u bilo kom trenutku se mozes vratiti na glavni meni)");
		
		clientOutput.println("Unesite korisnicko ime");
	
		try {
			korisnickoIme = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(korisnickoIme.equals("esc"))
			return null;
			
			pogresanUnos = false;
			for(int i=0; i < Server.klijenti.size(); i++) {
				if(Server.klijenti.get(i).korisnickoIme.equals(korisnickoIme)) {
					clientOutput.println("Korisnicko ime je vec zauzeto, pokusajte ponovo");
					pogresanUnos = true;
				}
			}
		}
		pogresanUnos = true;
		klijentP.korisnickoIme = korisnickoIme;

		String sifra = null;
		while(pogresanUnos) {
		clientOutput.println("Unesite sifru");
		try {
			sifra = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(sifra.equals("esc"))
			return null;
		if(sifra.length()>6)
			pogresanUnos = false;
		else clientOutput.println("Sifra mora biti duza od 6 karaktera");
		}
		
		klijentP.sifra = sifra;

		clientOutput.println("Unesite ime");
		String ime = null;
		try {
			ime = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(ime.equals("esc"))
			return null;
		klijentP.ime = ime;

		clientOutput.println("Unesite prezime");
		String prezime = null;
		try {
			prezime = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(prezime.equals("esc"))
			return null;
		klijentP.prezime = prezime;

		String jmbg = null;
		pogresanUnos = true;
		while(pogresanUnos) {
			clientOutput.println("Unesite JMBG");
		try {
			jmbg = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(jmbg.equals("esc"))
			return null;
		
		pogresanUnos = false;
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).JMBG.equals(jmbg)) {
				clientOutput.println("Korisnik sa ovim JMBGom vec postoji, pokusajte ponovo ili unesite esc za povratak u korisnicki meni");
				pogresanUnos = true;
				}
			}
			if(jmbg.length() != 13) {
				clientOutput.println("Duzina JMBGA treba biti 13 znakova, pokusajte ponovo");
				pogresanUnos = true;
			}
		}
		klijentP.JMBG = jmbg;
		pogresanUnos = true;

		String pol = null;
		while(pogresanUnos) {
			clientOutput.println("Unesite vas pol u formatu M/Ž: ");
		try {
			pol = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(pol.equals("esc"))
			return null;
		if(pol.equals("M") || pol.equals("Ž"))
			pogresanUnos = false;
		else clientOutput.println("Pogresno ste uneli pol, pokusajte ponovo");
		}
		klijentP.pol = pol;
		
		pogresanUnos = true;
		String email = null;
		
		while(pogresanUnos) {	
		clientOutput.println("Unesite e-mail");
		try {
			email = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(email.equals("esc"))
			return null;
		
		if(email.contains("@") && email.endsWith(".com")) {
			pogresanUnos = false;
		}
		else clientOutput.println("Vas email treba da sadrzi znak @ i da se zavrsava sa .com, pokusajte ponovo");
		}
		klijentP.email = email;
			
		pogresanUnos = true;
		
		String prvaDoza = null;
		
		while(pogresanUnos) {
			
			clientOutput.println("Da li ste vakcinisani prvom dozom vakcine? (1-da 0-ne)");
		try {
			prvaDoza = clientInput.readLine();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			if(prvaDoza.equals("esc")) {
						return klijentP;
				}
			
			if(prvaDoza.equals("0"))
				return klijentP;
			
			if(prvaDoza.equals("0") || prvaDoza.equals("1"))
				pogresanUnos = false;
			
			else clientOutput.println("Neispravan unos, pokusajte ponovo: ");
		}
		pogresanUnos = true;
		
		if (prvaDoza.equals("1")) {

			klijentP.prvaDoza = true;

			clientOutput.println("Unesite naziv prve primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca): ");
			String prvaDozaNaziv = null;
			
			while(pogresanUnos) {
			
				try {
				prvaDozaNaziv = clientInput.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				if(prvaDozaNaziv.equals("esc")) {
					klijentP.prvaDoza = false;
					return klijentP;
				}
				
				if(prvaDozaNaziv.equals("Pfizer") || prvaDozaNaziv.equals("SputnjikB") || prvaDozaNaziv.equals("Sinopharm") || prvaDozaNaziv.equals("AstraZeneca"))
					pogresanUnos = false;
				else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
			}
			
			pogresanUnos = true;
			klijentP.prvaDozaNaziv = prvaDozaNaziv;
			String datum = null;
			
			while(pogresanUnos) {
			clientOutput.println("Unesite datum prve vakcinacije u formatu DD/MM/GGGG: ");
			try {
				datum = clientInput.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(datum.equals("esc")) {
				klijentP.prvaDoza = false;
				klijentP.prvaDozaNaziv = null;
				return klijentP;
			}
			
			String[] datumNiz = datum.split("/");
			if(datumNiz.length < 3)
				clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
			
			else { 
				if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32)
					pogresanUnos = false;
				else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
				}
			}
			klijentP.datumPrveVakcinacije = datum;
			pogresanUnos = true;
			String drugaDoza = null;
			
			while(pogresanUnos) {
				
				clientOutput.println("Da li ste vakcinisani drugom dozom vakcine? (1-da 0-ne)");
				
				try {
					drugaDoza = clientInput.readLine();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(drugaDoza.equals("esc")) {
					return klijentP;
				}
					
				if(drugaDoza.equals("0") || drugaDoza.equals("1"))
					pogresanUnos = false;
					
				else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
				}
				pogresanUnos = true;

			if (drugaDoza.equals("0")) {
				klijentP.drugaDoza = false;
				return klijentP;
			}

			if (drugaDoza.equals("1")) {

				klijentP.drugaDoza = true;

				clientOutput.println("Unesite naziv druge primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca, Napomena: druga vakcina mora biti ista kao i prva): ");
				String drugaDozaNaziv = null;
				
				while(pogresanUnos) {
					
					try {
					drugaDozaNaziv = clientInput.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					if(drugaDozaNaziv.equals("esc")) {
						klijentP.drugaDoza = false;
						return klijentP;
					}
					
					if(drugaDozaNaziv.equals(prvaDozaNaziv))
						pogresanUnos = false;
					
					else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
				}
				
				pogresanUnos = true;
				klijentP.drugaDozaNaziv = drugaDozaNaziv;
				datum = null;
				
				while(pogresanUnos) {
					clientOutput.println("Unesite datum druge vakcinacije u formatu DD/MM/GGGG: ");
					try {
						datum = clientInput.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(datum.equals("esc")) {
						klijentP.drugaDoza = false;
						klijentP.drugaDozaNaziv = null;
						return klijentP;
					}
					
					String[] datumNiz = datum.split("/");
				    
					if(datumNiz.length < 3)
						clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
					
					else { 
						
						if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32) {

						SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

						try {
						    Date date1 = myFormat.parse(klijentP.datumPrveVakcinacije);
						    Date date2 = myFormat.parse(datum);
						    long diff = date2.getTime() - date1.getTime();
							
						    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 21) 
									pogresanUnos = false;
								
							else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
								
							} catch (ParseException e) {
						    e.printStackTrace();
						}
					}
						else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");		
				}
			}
					klijentP.datumDrugeVakcinacije = datum;
					pogresanUnos = true;

				String trecaDoza = null;
				
				while(pogresanUnos) {
					clientOutput.println("Da li ste vakcinisani trecom buster dozom vakcine? (1-da 0-ne): ");
					
					try {
						trecaDoza = clientInput.readLine();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
						if(trecaDoza.equals("esc")) {
							if(klijentP.prvaDoza == true && klijentP.drugaDoza == true && klijentP.prvaDozaNaziv.equals(klijentP.drugaDozaNaziv) && klijentP.trecaDoza == true) {
								klijentP.kovidPropusnicaValidna = true;
							}
							else if(klijentP.prvaDoza == true && klijentP.drugaDoza == true && klijentP.prvaDozaNaziv.equals(klijentP.drugaDozaNaziv)){
								klijentP.kovidPropusnicaValidna = true;
							}
							else {
								klijentP.kovidPropusnicaValidna = false;
							}
							klijentP.trecaDoza = false;
							return klijentP;
						}
						
						if(trecaDoza.equals("0") || trecaDoza.equals("1"))
							pogresanUnos = false;

						
						else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
					}
					pogresanUnos = true;

				if (trecaDoza.equals("0")) {
					klijentP.trecaDoza = false;
					return klijentP;
				}

				if (trecaDoza.equals("1")) {

					klijentP.trecaDoza = true;

					clientOutput.println("Unesite naziv trece primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca): ");
					String trecaDozaNaziv = null;
					while(pogresanUnos) {
						
						try {
						trecaDozaNaziv = clientInput.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						
						if(trecaDozaNaziv.equals("esc")) {
							if(klijentP.prvaDoza == true && klijentP.drugaDoza == true && klijentP.prvaDozaNaziv.equals(klijentP.drugaDozaNaziv) && klijentP.trecaDoza == true) {
								klijentP.kovidPropusnicaValidna = true;
							}
							else if(klijentP.prvaDoza == true && klijentP.drugaDoza == true && klijentP.prvaDozaNaziv.equals(klijentP.drugaDozaNaziv)){
								klijentP.kovidPropusnicaValidna = true;
							}
							else {
								klijentP.kovidPropusnicaValidna = false;
							}
							klijentP.trecaDoza = false;
							return klijentP;
						}
						
						if(trecaDozaNaziv.equals("Pfizer") || trecaDozaNaziv.equals("SputnjikB") || trecaDozaNaziv.equals("Sinopharm") || trecaDozaNaziv.equals("AstraZeneca"))
							pogresanUnos = false;
						
						else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
					}
					
					pogresanUnos = true;
					klijentP.trecaDozaNaziv = trecaDozaNaziv;
					
					datum = null;
					while(pogresanUnos) {
						clientOutput.println("Unesite datum trece vakcinacije u formatu DD/MM/GGGG: ");
						try {
							datum = clientInput.readLine();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(datum.equals("esc")) {
							klijentP.trecaDoza = false;
							klijentP.trecaDozaNaziv = null;
							
							if(klijentP.prvaDoza == true && klijentP.drugaDoza == true && klijentP.prvaDozaNaziv.equals(klijentP.drugaDozaNaziv)){
								klijentP.kovidPropusnicaValidna = true;
							}
							else {
								klijentP.kovidPropusnicaValidna = false;
							}
							return klijentP;
						}
						
						String[] datumNiz = datum.split("/");
					    
						if(datumNiz.length < 3)
							clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
						
						else { 
							
							if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32) {

							SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

							try {
							    Date date1 = myFormat.parse(klijentP.datumDrugeVakcinacije);
							    Date date2 = myFormat.parse(datum);
							    long diff = date2.getTime() - date1.getTime();
								
							    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 180) 
										pogresanUnos = false;
									
								else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
									
								} catch (ParseException e) {
							    e.printStackTrace();
							}
						}
							else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");		
					}
				}
						klijentP.datumTreceVakcinacije = datum;
						pogresanUnos = true;
				}
			}
		}
		klijentP.kovidPropusnicaValidna = true;
		return klijentP;
	}
	public KlijentPodaci prijavaKorisnika() {
		
		clientOutput.println("-------------PRIJAVA-------------");
		
		clientOutput.println("Unesite korisnicko ime: ");
		String korisnickoIme = null;
		try {
			korisnickoIme = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clientOutput.println("Unesite sifru: ");
		String sifra = null;
		try {
			sifra = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		KlijentPodaci k = new KlijentPodaci();
		k.korisnickoIme = korisnickoIme;
		k.sifra = sifra;
		
		return k;
	}

	public void proveraValidnostiJMBG(){
		
		
		clientOutput.println("Unesite JMBG: ");
		
		String jmbg = null;

		try {
			jmbg = clientInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).JMBG.equals(jmbg)) {
				kovidPropusnicaValidnost(Server.klijenti.get(i));
				return;
			}
		}
		clientOutput.println("Unet je pogresan JMBG!");
		
		return;
	}
	
	public void prikaziListu() {
		
		clientOutput.println("------------------------------------------------Lista svih korisnika--------------------------------------------------");
		for(int i=0; i<Server.klijenti.size(); i++) {
			
			int doza = 0;
			if(Server.klijenti.get(i).prvaDoza == true)
				doza = 1;
			if(Server.klijenti.get(i).drugaDoza == true)
				doza = 2;
			if(Server.klijenti.get(i).trecaDoza == true)
				doza = 3;
			clientOutput.print("Ime:"+Server.klijenti.get(i).ime +" |Prezime:"+Server.klijenti.get(i).prezime + " |JMBG:"+Server.klijenti.get(i).JMBG + " |Broj primljenih doza:"+doza);
			if(doza == 0)
				clientOutput.println();
			if(doza == 1)
				clientOutput.println(" |Datum prve vakcinacije:"+Server.klijenti.get(i).datumPrveVakcinacije);
			else if(doza == 2)
				clientOutput.println(" |Datum prve vakcinacije:"+Server.klijenti.get(i).datumPrveVakcinacije+" |Datum druge vakcinacije:"+Server.klijenti.get(i).datumDrugeVakcinacije);
			else if(doza == 3)
				clientOutput.println(" |Datum prve vakcinacije:"+Server.klijenti.get(i).datumPrveVakcinacije+" |Datum druge vakcinacije:"+Server.klijenti.get(i).datumDrugeVakcinacije+" |Datum trece vakcinacije:"+Server.klijenti.get(i).datumTreceVakcinacije);
		}
		clientOutput.println("-----------------------------------------------------------------------------------------------------------------------");
		return;
	}

	public void brojKorisnikaDoza() {
		
		int brojac = 0;
		clientOutput.println("-------Broj korisnika vakcinisanih sa jednom dozom vakcine: -------");
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).trecaDoza == false && Server.klijenti.get(i).drugaDoza == false && Server.klijenti.get(i).prvaDoza == true) {
				brojac++;
			}
		}
		clientOutput.println("Ukupno: "+brojac);
		
		brojac = 0;
		clientOutput.println("-------Broj korisnika vakcinisanih sa dve doze vakcine: -------");
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).trecaDoza == false && Server.klijenti.get(i).drugaDoza == true && Server.klijenti.get(i).prvaDoza == true) {
			brojac++;
			}
		}
		clientOutput.println("Ukupno: "+brojac);

		brojac = 0;
		clientOutput.println("-------Broj korisnika vakcinisanih sa tri doze vakcine: -------");
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).trecaDoza == true && Server.klijenti.get(i).drugaDoza == true && Server.klijenti.get(i).prvaDoza == true) {
			brojac++;
			}
		}
		clientOutput.println("Ukupno: "+brojac);

	}

	public void dveDoze() {
		
		int brojac = 0;
		clientOutput.println("Broj korisnika vakcinisanih sa dve doze vakcine \"Pfizer\" ");
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).drugaDoza == true && Server.klijenti.get(i).drugaDozaNaziv.equals("Pfizer")) {
			brojac++;
			}
		}
		clientOutput.println("Ukupno: " +brojac);
		
		clientOutput.println("Broj korisnika vakcinisanihi sa dve doze vakcine \"SputnjikB\" ");
		brojac = 0;
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).drugaDoza == true && Server.klijenti.get(i).drugaDozaNaziv.equals("SputnjikB")) {
			brojac++;
			}
		}
		clientOutput.println("Ukupno: " +brojac);

		
		clientOutput.println("Broj korisnika vakcinisanih sa dve doze vakcine \"Sinopharm\" ");
		brojac = 0;
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).drugaDoza == true && Server.klijenti.get(i).drugaDozaNaziv.equals("Sinopharm")) {
			brojac++;
			}
		}
		clientOutput.println("Ukupno: " +brojac);

		
		clientOutput.println("Broj korisnika vakcinisanih sa dve doze vakcine \"AstraZeneca\" ");
		brojac = 0;
		for(int i=0; i<Server.klijenti.size(); i++) {
			if(Server.klijenti.get(i).drugaDoza == true && Server.klijenti.get(i).drugaDozaNaziv.equals("AstraZeneca")) {
			brojac++;
			}
		}
		clientOutput.println("Ukupno: " +brojac);
	}
	
	public void AdminNalog() {
		
		while(true) {
			
			clientOutput.println("-------------Zdravo!-------------");
			clientOutput.println("1. Provera validnosti kovid propusnice unosom JMBGa");
			clientOutput.println("2. Prikazi listu svih korisnika i njihov status vakcinacije");
			clientOutput.println("3. Prikazi podatke o broju korisnika koji su primili vakcine u odnosu na broj doza vakcina");
			clientOutput.println("4. Prikazi podatke o korisnicima koji su primili dve doze vakcina u odnosu na proizvodjace vakcina");
			clientOutput.println("5. Odjava");
			clientOutput.println("Odaberite jednu od ponudjenih opcija unosom broja: ");
			
			try {
				izbor = Integer.parseInt(clientInput.readLine());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(izbor) {
			case 1:
				proveraValidnostiJMBG();
				break;
			case 2:
				prikaziListu();
				break;
			case 3:
				brojKorisnikaDoza();
				break;
			case 4:
				dveDoze();
				break;
			case 5:
				return;
			}
		}
	}
	
	public void generisiKovidPropusnicu(KlijentPodaci novi) {
		
		if(novi.drugaDoza == false) {
			clientOutput.println("Korisnik ne moze da generise kovid propusnicu!");
			return;
		}
		
		//upisujemo podatke u txt datotetci
		PrintWriter out = null;
		try {
			String imePrezimeJMBG = novi.ime+novi.prezime+novi.JMBG;
			out = new PrintWriter(new BufferedWriter(new FileWriter(imePrezimeJMBG+".txt",false)));
				out.println();
				out.println("---------------KOVID PROPUSNICA---------------");
				out.println();
				out.println();
				out.println();
				out.println("Ime:"+novi.ime+"     Prezime:"+novi.prezime+"     JMBG:"+novi.JMBG);
				out.println();
				out.println("Naziv primljene prve vakcine:"+novi.prvaDozaNaziv);
				out.println("Datum primljene prve vakcine:"+novi.datumPrveVakcinacije);
				out.println();
				out.println("Naziv primljene druge vakcine:"+novi.drugaDozaNaziv);
				out.println("Datum primljene druge vakcine:"+novi.datumDrugeVakcinacije);
				out.println();
				if(novi.trecaDoza == true) {
					out.println("Naziv primljene trece vakcine:"+novi.trecaDozaNaziv);
					out.println("Datum primljene trece vakcine:"+novi.datumTreceVakcinacije);
					out.println();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(out != null)
				out.close();
		}
		clientOutput.println("Uspesno generisana kovid propusnica!");
		return;
	}

	public KlijentPodaci nalog(KlijentPodaci klijent) {
		
		KlijentPodaci novi = klijent;
		
		while(true) {
		clientOutput.println("-------------Zdravo, "+klijent.ime+"!-------------");
		clientOutput.println("1. Izmena odgovora na postavljena pitanja prilikom registracije");
		clientOutput.println("2. Provera validnosti kovid propusnice");
		clientOutput.println("3. Generisanje kovid propusnice");
		clientOutput.println("4. Odjava");
		clientOutput.println("Odaberite jednu od ponudjenih opcija unosom broja:");

		String izborS = null;

		int broj = -1;
		boolean pogresanUnos = true;
		
		while(pogresanUnos) {
			try {
				izborS = clientInput.readLine();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     try {  
             broj = Integer.parseInt(izborS);  
	     }catch(NumberFormatException ex){  
	    	 clientOutput.println();  
	    	 //request for well-formatted string  
	     	}  

	     if(broj == 1 || broj == 2 || broj == 3 || broj == 4)
	    	 pogresanUnos = false;
	     else
	    	 clientOutput.println("Neispravan unos, pokusaj ponovo!");
		}
		
		switch(broj) {
		case 1:
			if(novi.trecaDoza == true)
				clientOutput.println("Na sva pitanja ste odgovorili!");
			else novi = izmenaOdgovora(novi);
			break;
			
		case 2:
			kovidPropusnicaValidnost(novi);
			break;
		
		case 3:
			generisiKovidPropusnicu(novi);
			break;
		case 4:
			clientOutput.println("Uspesno ste se odjavili");
			return novi;
			}
		}
	}

	public KlijentPodaci izmenaOdgovora(KlijentPodaci klijent) {
		
		KlijentPodaci pom = klijent;
		
		clientOutput.println("--------Pred vama su pitanja na koja ste dali negativan odgovor. Svi uneti odgovori ce biti zabelezeni kao novi i izmenjeni!--------");
		clientOutput.println("(Unosom \"esc\" u bilo kom trenutku se mozes vratiti na korisnicki meni)");
		
		boolean pogresanUnos = true;
		
		if(pom.prvaDoza == false) {
			
			clientOutput.println("Da li ste vakcinisani prvom dozom vakcine? (1-da 0-ne)");
			String prvaDoza = null;
			while(pogresanUnos) {
				
				try {
					prvaDoza = clientInput.readLine();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(prvaDoza.equals("esc")) {
					return pom;
				}
					
					if(prvaDoza.equals("0") || prvaDoza.equals("1"))
						pogresanUnos = false;
					
					else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
				}
				pogresanUnos = true;

			if (prvaDoza.equals("0"))
				pom.prvaDoza = false;

			if (prvaDoza.equals("1")) {

				pom.prvaDoza = true;

				clientOutput.println("Unesite naziv primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca):");
				String prvaDozaNaziv = null;
				while(pogresanUnos) {
					
					try {
					prvaDozaNaziv = clientInput.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					
					if(prvaDozaNaziv.equals("esc")) {
						pom.prvaDoza = false;
						return pom;
					}
					
					if(prvaDozaNaziv.equals("Pfizer") || prvaDozaNaziv.equals("SputnjikB") || prvaDozaNaziv.equals("Sinopharm") || prvaDozaNaziv.equals("AstraZeneca"))
						pogresanUnos = false;	
					
					else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
				}
				pogresanUnos = true;
				pom.prvaDozaNaziv = prvaDozaNaziv;
				
				String datum = null;
				while(pogresanUnos) {
				clientOutput.println("Unesite datum vakcinacije u formatu DD/MM/GGGG: ");
				try {
					datum = clientInput.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(datum.equals("esc")) {
					pom.prvaDoza = false;
					pom.prvaDozaNaziv = null;
					return pom;
				}
				
				String[] datumNiz = datum.split("/");
				if(datumNiz.length < 3)
					clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
				
				else { 
					if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32)
						pogresanUnos = false;
					else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
					}
				}
				pom.datumPrveVakcinacije = datum;
				pogresanUnos = true;

				clientOutput.println("Da li ste vakcinisani drugom dozom vakcine? (1-da 0-ne)");
				
				String drugaDoza = null;
				
				while(pogresanUnos) {
					
					try {
						drugaDoza = clientInput.readLine();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(drugaDoza.equals("esc")) {
						return pom;
					}
						
						if(drugaDoza.equals("0") || drugaDoza.equals("1"))
							pogresanUnos = false;
						
						else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
					}
					pogresanUnos = true;

			if (drugaDoza.equals("0"))
				pom.drugaDoza = false;

			if (drugaDoza.equals("1")) {

				pom.drugaDoza = true;

				clientOutput.println("Unesite naziv primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca, Napomena: druga vakcina mora biti ista kao i prva): ");
				String drugaDozaNaziv = null;
				while(pogresanUnos) {
					
					try {
					drugaDozaNaziv = clientInput.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					
					if(drugaDozaNaziv.equals("esc")) {
						pom.drugaDoza = false;
						return pom;
					}
					
					if(drugaDozaNaziv.equals(prvaDozaNaziv))
						pogresanUnos = false;	
					
					else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
				}
				pogresanUnos = true;
				pom.drugaDozaNaziv = drugaDozaNaziv;
				
				datum = null;
				while(pogresanUnos) {
					clientOutput.println("Unesite datum vakcinacije u formatu DD/MM/GGGG: ");
					try {
						datum = clientInput.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(datum.equals("esc")) {
						pom.drugaDoza = false;
						pom.drugaDozaNaziv = null;
						return pom;
					}
					
					String[] datumNiz = datum.split("/");
				    
					if(datumNiz.length < 3)
						clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
					
					else { 
						
						if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32) {

						SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

						try {
						    Date date1 = myFormat.parse(pom.datumPrveVakcinacije);
						    Date date2 = myFormat.parse(datum);
						    long diff = date2.getTime() - date1.getTime();
							
						    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 21) 
									pogresanUnos = false;
								
							else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
								
							} catch (ParseException e) {
						    e.printStackTrace();
						}
					}
						else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");		
				}
			}
					pom.datumDrugeVakcinacije = datum;
					pogresanUnos = true;

				clientOutput.println("Da li ste vakcinisani trecom buster dozom vakcine? (1-da 0-ne)");
				String trecaDoza = null;

				while(pogresanUnos) {
					
					try {
						trecaDoza = clientInput.readLine();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(trecaDoza.equals("esc")) {
						if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
							pom.kovidPropusnicaValidna = true;
						}
						else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
							pom.kovidPropusnicaValidna = true;
						}
						else {
							pom.kovidPropusnicaValidna = false;
						}
						return pom;
					}
						
						if(trecaDoza.equals("0") || trecaDoza.equals("1"))
							pogresanUnos = false;
						
						else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
					}
					pogresanUnos = true;

				if (trecaDoza.equals("0"))
					pom.trecaDoza = false;

				if (trecaDoza.equals("1")) {

					pom.trecaDoza = true;

					clientOutput.println("Unesite naziv primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca): ");
					String trecaDozaNaziv = null;
					while(pogresanUnos) {
						
						try {
							trecaDozaNaziv = clientInput.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						
						if(trecaDozaNaziv.equals("esc")) {
							if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
								pom.kovidPropusnicaValidna = true;
							}
							else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
								pom.kovidPropusnicaValidna = true;
							}
							else {
								pom.kovidPropusnicaValidna = false;
							}
							pom.trecaDoza = false;
							return pom;
						}
						
						if(trecaDozaNaziv.equals("Pfizer") || trecaDozaNaziv.equals("SputnjikB") || trecaDozaNaziv.equals("Sinopharm") || trecaDozaNaziv.equals("AstraZeneca"))
							pogresanUnos = false;	
						
						else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
					}
					pogresanUnos = true;
					pom.trecaDozaNaziv = trecaDozaNaziv;
					
					datum = null;
					while(pogresanUnos) {
						clientOutput.println("Unesite datum vakcinacije u formatu DD/MM/GGGG: ");
						try {
							datum = clientInput.readLine();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(datum.equals("esc")) {
							if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
								pom.kovidPropusnicaValidna = true;
							}
							else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
								pom.kovidPropusnicaValidna = true;
							}
							else {
								pom.kovidPropusnicaValidna = false;
							}
							pom.trecaDoza = false;
							pom.trecaDozaNaziv = null;
							return pom;
						}
						
						String[] datumNiz = datum.split("/");
					    
						if(datumNiz.length < 3)
							clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
						
						else { 
							
							if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32) {

							SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

							try {
							    Date date1 = myFormat.parse(pom.datumDrugeVakcinacije);
							    Date date2 = myFormat.parse(datum);
							    long diff = date2.getTime() - date1.getTime();
								
							    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 180) 
										pogresanUnos = false;
									
								else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
									
								} catch (ParseException e) {
							    e.printStackTrace();
							}
						}
							else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");		
					}
				}
						pom.datumTreceVakcinacije = datum;
						pogresanUnos = true;
				}
			}
		}
	}
		
		if(pom.drugaDoza == false) {
			
			clientOutput.println("Da li ste vakcinisani drugom dozom vakcine? (1-da 0-ne)");
			String drugaDoza = null;

			while(pogresanUnos) {
				
				try {
					drugaDoza = clientInput.readLine();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				if(drugaDoza.equals("esc")) {
					return pom;
				}
				
					if(drugaDoza.equals("0") || drugaDoza.equals("1"))
						pogresanUnos = false;
					
					else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
				}
				pogresanUnos = true;
			
			if (drugaDoza.equals("0"))
				pom.drugaDoza = false;

			else if (drugaDoza.equals("1")) {

				pom.drugaDoza = true;

				clientOutput.println("Unesite naziv primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca): ");
				String drugaDozaNaziv = null;
				while(pogresanUnos) {
					
					try {
						drugaDozaNaziv = clientInput.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					if(drugaDozaNaziv.equals("esc")) {
						pom.drugaDoza = false;
						return pom;
					}
					if(drugaDozaNaziv.equals(klijent.prvaDozaNaziv))
						pogresanUnos = false;	
					
					else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
				}
				pogresanUnos = true;
				pom.drugaDozaNaziv = drugaDozaNaziv;
				
				String datum;
				datum = null;
				while(pogresanUnos) {
					clientOutput.println("Unesite datum vakcinacije u formatu DD/MM/GGGG: ");
					try {
						datum = clientInput.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(datum.equals("esc")) {
						pom.drugaDoza = false;
						pom.drugaDozaNaziv = null;
						return pom;
					}
					
					String[] datumNiz = datum.split("/");
				    
					if(datumNiz.length < 3)
						clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
					
					else { 
						
						if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32) {

						SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

						try {
						    Date date1 = myFormat.parse(pom.datumPrveVakcinacije);
						    Date date2 = myFormat.parse(datum);
						    long diff = date2.getTime() - date1.getTime();
							
						    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 21) 
									pogresanUnos = false;
								
							else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
								
							} catch (ParseException e) {
						    e.printStackTrace();
						}
					}
						else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");		
				}
			}
					pom.datumDrugeVakcinacije = datum;
					pogresanUnos = true;

				clientOutput.println("Da li ste vakcinisani trecom buster dozom vakcine? (1-da 0-ne)");
				String trecaDoza = null;

				while(pogresanUnos) {
					
					try {
						trecaDoza = clientInput.readLine();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(trecaDoza.equals("esc")) {
						if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
							pom.kovidPropusnicaValidna = true;
						}
						else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
							pom.kovidPropusnicaValidna = true;
						}
						else {
							pom.kovidPropusnicaValidna = false;
						}
						return pom;
					}
						
						if(trecaDoza.equals("0") || trecaDoza.equals("1"))
							pogresanUnos = false;
						
						else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
					}
					pogresanUnos = true;

				if (trecaDoza.equals("0"))
					pom.trecaDoza = false;

				if (trecaDoza.equals("1")) {

					pom.trecaDoza = true;
					clientOutput.println("Unesite naziv primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca): ");
					String trecaDozaNaziv = null;
					
					while(pogresanUnos) {

					try {
						trecaDozaNaziv = clientInput.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						if(trecaDozaNaziv.equals("esc")) {
							if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
								pom.kovidPropusnicaValidna = true;
							}
							else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
								pom.kovidPropusnicaValidna = true;
							}
							else {
								pom.kovidPropusnicaValidna = false;
							}
							pom.trecaDoza = false;
							return pom;
						}
						
						if(trecaDozaNaziv.equals("Pfizer") ||trecaDozaNaziv.equals("SputnjikB") || trecaDozaNaziv.equals("Sinopharm")|| trecaDozaNaziv.equals("AstraZeneca")) {
							pogresanUnos = false;
						}
						else clientOutput.println("Neispravan unos naziva vakcine, pokusajte ponovo: ");

					}
					pogresanUnos = true;
					pom.trecaDozaNaziv = trecaDozaNaziv;
					
					datum = null;
					while(pogresanUnos) {
						clientOutput.println("Unesite datum vakcinacije u formatu DD/MM/GGGG: ");
						try {
							datum = clientInput.readLine();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(datum.equals("esc")) {
							if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
								pom.kovidPropusnicaValidna = true;
							}
							else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
								pom.kovidPropusnicaValidna = true;
							}
							else {
								pom.kovidPropusnicaValidna = false;
							}
							pom.trecaDoza = false;
							pom.trecaDozaNaziv = null;
							return pom;
						}
						
						String[] datumNiz = datum.split("/");
					    
						if(datumNiz.length < 3)
							clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
						
						else { 
							
							if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32) {

							SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

							try {
							    Date date1 = myFormat.parse(pom.datumDrugeVakcinacije);
							    Date date2 = myFormat.parse(datum);
							    long diff = date2.getTime() - date1.getTime();
								
							    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 180) 
										pogresanUnos = false;
									
								else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
									
								} catch (ParseException e) {
							    e.printStackTrace();
							}
						}
							else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");		
					}
				}
						pom.datumTreceVakcinacije = datum;
						pogresanUnos = true;
				}
			}
		}
		
		else if(pom.trecaDoza == false) {
			
			clientOutput.println("Da li ste vakcinisani trecom buster dozom vakcine? (1-da 0-ne)");
			String trecaDoza = null;

			while(pogresanUnos) {
				
				try {
					trecaDoza = clientInput.readLine();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(trecaDoza.equals("esc")) {
					if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
						pom.kovidPropusnicaValidna = true;
					}
					else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
						pom.kovidPropusnicaValidna = true;
					}
					else {
						pom.kovidPropusnicaValidna = false;
					}
					return pom;
				}
					
					if(trecaDoza.equals("0") || trecaDoza.equals("1"))
						pogresanUnos = false;
					
					else clientOutput.println("Neispravan unos broja, pokusajte ponovo: ");
				}
				pogresanUnos = true;

			if (trecaDoza.equals("0"))
				pom.trecaDoza = false;

			if (trecaDoza.equals("1")) {

				pom.trecaDoza = true;

				clientOutput.println("Unesite naziv primljene vakcine (Pfizer, SputnjikB, Sinopharm ili AstraZeneca): ");
				String trecaDozaNaziv = null;
				while(pogresanUnos) {
					
					try {
						trecaDozaNaziv = clientInput.readLine();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					
					if(trecaDozaNaziv.equals("esc")) {
						if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
							pom.kovidPropusnicaValidna = true;
						}
						else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
							pom.kovidPropusnicaValidna = true;
						}
						else {
							pom.kovidPropusnicaValidna = false;
						}
						pom.trecaDoza = false;
						return pom;
					}
					
					if(trecaDozaNaziv.equals("Pfizer") || trecaDozaNaziv.equals("SputnjikB") || trecaDozaNaziv.equals("Sinopharm") || trecaDozaNaziv.equals("AstraZeneca"))
						pogresanUnos = false;	
					
					else clientOutput.println("Neispravan unos vakcine, pokusajte ponovo: ");
				}
				pogresanUnos = true;
				pom.trecaDozaNaziv = trecaDozaNaziv;
				
				String datum = null;
				while(pogresanUnos) {
					clientOutput.println("Unesite datum vakcinacije u formatu DD/MM/GGGG: ");
					try {
						datum = clientInput.readLine();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(datum.equals("esc")) {
						if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
							pom.kovidPropusnicaValidna = true;
						}
						else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
							pom.kovidPropusnicaValidna = true;
						}
						else {
							pom.kovidPropusnicaValidna = false;
						}
						pom.trecaDoza = false;
						pom.trecaDozaNaziv = null;
						return pom;
					}
					
					String[] datumNiz = datum.split("/");
				    
					if(datumNiz.length < 3)
						clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
					
					else { 
						
						if(Integer.parseInt(datumNiz[2]) == 2021 && Integer.parseInt(datumNiz[1]) > 0 && Integer.parseInt(datumNiz[1]) < 13 && Integer.parseInt(datumNiz[0]) > 0 && Integer.parseInt(datumNiz[0]) < 32) {

						SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

						try {
						    Date date1 = myFormat.parse(pom.datumDrugeVakcinacije);
						    Date date2 = myFormat.parse(datum);
						    long diff = date2.getTime() - date1.getTime();
							
						    if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 180) 
									pogresanUnos = false;
								
							else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");
								
							} catch (ParseException e) {
						    e.printStackTrace();
						}
					}
						else clientOutput.println("Neispravan unos datuma, pokusajte ponovo: ");		
				}
			}
					pom.datumTreceVakcinacije = datum;
					pogresanUnos = true;
			}
		}
		
		if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv) && pom.trecaDoza == true) {
			pom.kovidPropusnicaValidna = true;
		}
		else if(pom.prvaDoza == true && pom.drugaDoza == true && pom.prvaDozaNaziv.equals(pom.drugaDozaNaziv)){
			pom.kovidPropusnicaValidna = true;
		}
		else {
			pom.kovidPropusnicaValidna = false;
		}
		return pom;
	}

	public void kovidPropusnicaValidnost(KlijentPodaci novi){
		
		if(novi.prvaDoza == null)
			return;
		if(novi.drugaDoza == null)
			return;
		
		if(novi.prvaDoza == true && novi.drugaDoza == true && novi.prvaDozaNaziv.equals(novi.drugaDozaNaziv) && novi.trecaDoza == true) {
			clientOutput.println("### Korisnik POSEDUJE validnu kovid propusnicu! ###");
		}
		
		else if(novi.prvaDoza == true && novi.drugaDoza == true && novi.prvaDozaNaziv.equals(novi.drugaDozaNaziv)){
			clientOutput.println("### Korisnik POSEDUJE validnu kovid propusnicu! ###");
		}
		
		else {
			clientOutput.println("### Korisnik NE POSEDUJE validnu kovid propusnicu! ###");
		}
	}

	public int meni() {
					
		
		clientOutput.println("-------------MENI-------------");
		clientOutput.println("1. Registracija na sistem");
		clientOutput.println("2. Prijava na sistem");
		clientOutput.println("-1. Izlazak");
		clientOutput.println("Odaberite jednu od ponudjenih opcija unosom broja: ");
		
		String izborS = null;

		int broj = -1;
		boolean pogresanUnos = true;
		
		while(pogresanUnos) {
			try {
				izborS = clientInput.readLine();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     try {  
             broj = Integer.parseInt(izborS);  
	     }catch(NumberFormatException ex){  
	    	 clientOutput.println();  
	    	 //request for well-formatted string  
	     	}  

	     if(broj == 1 || broj == 2 || broj == -1)
	    	 pogresanUnos = false;
	     else
	    	 clientOutput.println("Neispravan unos, pokusaj ponovo!");
		}
		
		switch (broj) {

		case 1:
			
			KlijentPodaci kl = registracijaKorisnika();
			if(kl == null) {
				clientOutput.println("Registracija NIJE uspela!");
			}
			else{ 
				
			Server.klijenti.add(kl);
			//upisujemo podatke u txt datotetci
			PrintWriter out = null;
			try {
				out = new PrintWriter(new BufferedWriter(new FileWriter("korisnici.txt",false)));
				for(int i=0; i<Server.klijenti.size(); i++) {
					out.println(Server.klijenti.get(i).korisnickoIme+" "+Server.klijenti.get(i).sifra+" "+Server.klijenti.get(i).ime+" "+Server.klijenti.get(i).prezime+" "+Server.klijenti.get(i).JMBG+" "+Server.klijenti.get(i).pol+" "+Server.klijenti.get(i).email+" "+Server.klijenti.get(i).prvaDoza+" "+Server.klijenti.get(i).drugaDoza+" "+Server.klijenti.get(i).trecaDoza+" "+Server.klijenti.get(i).prvaDozaNaziv+" "+Server.klijenti.get(i).drugaDozaNaziv+" "+Server.klijenti.get(i).trecaDozaNaziv+" "+Server.klijenti.get(i).kovidPropusnicaValidna+" "+Server.klijenti.get(i).datumPrveVakcinacije+" "+Server.klijenti.get(i).datumDrugeVakcinacije+" "+Server.klijenti.get(i).datumTreceVakcinacije+" ");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(out != null)
					out.close();
				}
			clientOutput.println("Registracija je uspesna!");
			}
			break;

		case 2:
			
			KlijentPodaci k = prijavaKorisnika();
			
			if(k.korisnickoIme.equals("admin") && k.sifra.equals("admin")) {
				AdminNalog();
			}
			else {
				boolean postoji = false;
				for(int i=0; i<Server.klijenti.size(); i++) {
				if(Server.klijenti.get(i).korisnickoIme.equals(k.korisnickoIme) && Server.klijenti.get(i).sifra.equals(k.sifra)) {
					postoji = true;
					KlijentPodaci novi = nalog(Server.klijenti.get(i));
					Server.klijenti.set(i, novi);
					
					//upisujemo podatke u txt datotetci
					PrintWriter out = null;
					try {
						out = new PrintWriter(new BufferedWriter(new FileWriter("korisnici.txt",false)));
						for(i=0; i<Server.klijenti.size(); i++) {
							out.println(Server.klijenti.get(i).korisnickoIme+" "+Server.klijenti.get(i).sifra+" "+Server.klijenti.get(i).ime+" "+Server.klijenti.get(i).prezime+" "+Server.klijenti.get(i).JMBG+" "+Server.klijenti.get(i).pol+" "+Server.klijenti.get(i).email+" "+Server.klijenti.get(i).prvaDoza+" "+Server.klijenti.get(i).drugaDoza+" "+Server.klijenti.get(i).trecaDoza+" "+Server.klijenti.get(i).prvaDozaNaziv+" "+Server.klijenti.get(i).drugaDozaNaziv+" "+Server.klijenti.get(i).trecaDozaNaziv+" "+Server.klijenti.get(i).kovidPropusnicaValidna+" "+Server.klijenti.get(i).datumPrveVakcinacije+" "+Server.klijenti.get(i).datumDrugeVakcinacije+" "+Server.klijenti.get(i).datumTreceVakcinacije+" ");
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if(out != null)
							out.close();
						}
					}
				}
				if(!postoji) 
					clientOutput.println("Pogresno korisnicko ime ili lozinka!");
			}
			break;
			
		case -1:
			clientOutput.println("Uspesno ste izasli");
			return -1;
			
		default:
			break;
			
			}
		return 0;
	}
	 
	// Metoda run u kojoj se izvrsava NIT
	@Override
	public void run() {
		
		try {
			
			//inicijalizacija ulazno - izlaznih tokova
			clientInput = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			clientOutput = new PrintStream(soketZaKomunikaciju.getOutputStream());
			
			//prikazi meni korisniku
			while(true) {
				int pozoviMeni = meni();
				
				if(pozoviMeni == -1) {
					clientOutput.println("Uspesno ste izasli");
					return;
				}
			}
			
			
		} catch (IOException e) {
			
		}
		
	}

}
