package Models;

import java.util.*;
import Observables.MyObservable;
import java.io.*;

public class Group extends MyObservable implements Serializable{
	private static final long serialVersionUID = 1L;
	private boolean changed = false;
	public String nom, description;
	public List<Contact> contacts;

	
	public Group() {
		this.contacts = new ArrayList<>();
	}
	
	public Group(String nom, String description){
		this.nom = nom;
		this.description = description;
		this.contacts = new ArrayList<>();
	}
	
	public void ajouterContact(Contact contact) {
		if(!contacts.contains(contact)) 
			contacts.add(contact);
        setChanged();
        notifyObservers();
        changed = false;
	}
	
	public void deleteContact(Contact contact) {
		contacts.remove(contact);
        setChanged();
        notifyObservers();
        changed = false;
	}

    public String getNom() {
        return nom;
    }
    
    public String getDescription() {
        return description;
    }
    

    public List<Contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }

    public int getNombreContacts() {
    	if (contacts == null) return 0;
			return contacts.size();
    }

    public String toString() {
    	return getNom() + " with " + getNombreContacts() + " contacts";
    }
}