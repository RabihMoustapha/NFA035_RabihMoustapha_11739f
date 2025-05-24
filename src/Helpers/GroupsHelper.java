package Helpers;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Models.Groupe;

public class GroupsHelper {
	public Set<Groupe> loadGroupsFromFile() {
	    Set<Groupe> groupes = new HashSet<>();
	    File file = new File("Groups.dat");
	    if (file.exists()) {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
	            Object obj = ois.readObject();
	            groupes.addAll((Collection<? extends Groupe>) obj);
	            ois.close();
	        } catch (IOException | ClassNotFoundException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return groupes;
	}
    
    public void saveGroupsToFile(DefaultListModel<Groupe> listModel) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("Groups.dat"))) {
        	int i = 0;
            oos.writeObject((Object) listModel.get(i));
            oos.writeObject("\n");
            i++;
            oos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeFromList(DefaultListModel<Groupe> listModel, int index) {
        if (index >= 0 && index < listModel.getSize()) {
            listModel.remove(index);
        }
    }
}
