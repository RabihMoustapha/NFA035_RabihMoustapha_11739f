package Models;

import java.util.*;
import Models.Contact;
import Models.Groupe;

public class DataClass {
	public Set<Contact> contacts;
	public Set<Groupe> groups;
	
	public DataClass() {
		this.contacts = new HashSet<>();
		this.groups = new HashSet<>();
	}
}
