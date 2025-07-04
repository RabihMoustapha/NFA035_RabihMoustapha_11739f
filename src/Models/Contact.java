package Models;

import Observables.MyObservable;
import java.util.*;
import java.io.*;



public class Contact extends MyObservable implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean changed = false;
	public String nom, prenom, ville;
	public List<PhoneNumber> telephoneNumbers;

	public Contact() {
		this.telephoneNumbers = new ArrayList<>();
	}

	public Contact(String nom, String prenom, String ville) {
		this.nom = nom;
		this.prenom = prenom;
		this.ville = ville;
		this.telephoneNumbers = new ArrayList<>();
	}

	public void addPhoneNumber(PhoneNumber pn) {
		this.telephoneNumbers.add(pn);
		setChanged();
		notifyObservers();
		changed = false;
	}

	public void deletePhoneNumber(PhoneNumber number) {
		if(this.telephoneNumbers.contains(number))
			this.telephoneNumbers.remove(number);
		setChanged();
		notifyObservers();
		changed = false;
	}

	public String getNom() {
		return this.nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public String getVille() {
		return this.ville;
	}
	
	public List<PhoneNumber> getNumbers() {
		return this.telephoneNumbers;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String toString() {
		return this.nom + " " + this.prenom + " lives in " + this.ville;
	}
}
