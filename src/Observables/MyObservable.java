package Observables;
import Observers.MyObserver;
import java.util.*;

public abstract class MyObservable {
	boolean changed;
	List<MyObserver> observers;
	
	public MyObservable(){
		changed = false;
		observers = new ArrayList<MyObserver>();
	}
	
	public void setChanged() { changed = true; }

	public void addObserver(MyObserver ob) { observers.add(ob); }

	public void notifyObservers() {
		for(Iterator<MyObserver> it = observers.iterator(); it.hasNext();) 
		    ((MyObserver) it.next()).update();
			changed = false;
	}
}