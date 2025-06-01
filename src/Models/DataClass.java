package Models;

import java.util.*;
import Models.Contact;
import Models.Groupe;

public class DataClass {
	public List<Contact> contacts;
	public List<Groupe> groups;
	
	public DataClass() {
		this.contacts = new ArrayList<>();
		this.groups = new ArrayList<>();
	}
}
