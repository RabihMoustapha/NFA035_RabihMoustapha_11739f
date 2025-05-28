package Models;
import java.util.*;
import Observables.MyObservable;

public class Groupe extends MyObservable {
    private boolean changed = false;
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
            setChanged();
            notifyObservers();
            changed = false;
		}
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
    

    public Set<Contact> getContacts() {
        return new HashSet<>(contacts);
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