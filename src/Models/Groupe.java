package Models;

import java.io.*;
import java.util.*;

public class Groupe implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nom, description;
	private static Set<Contact> contacts;

	public Groupe(String nom, String description){
		this.nom = nom;
		this.description = description;
		this.contacts = new HashSet<>();
	}
	
	public void ajouterContact(Contact contact) {
		if(!contacts.contains(contact)) {
			contacts.add(contact);
		}
	}
	
	public void retirerContact(Contact contact) {
		contacts.remove(contact);
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

    public static int getNombreContacts() {
			return contacts.size();
    }

    public String toString() {
        return "Groupe [nom=" + nom + ", description=" + description 
             + ", nombreContacts=" + getNombreContacts() + "]";
    }
}