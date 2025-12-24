package Observables;
import Observers.MyObserver;
import java.util.*;

public abstract class MyObservable {
	private boolean changed;
	private final List<MyObserver> observers;

	public MyObservable() {
		changed = false;
		observers = new ArrayList<MyObserver>();
	}

	public void setChanged() {
		changed = true;
	}

	public void addObserver(MyObserver ob) {
		if (ob == null) return;
		if (!observers.contains(ob)) observers.add(ob);
	}

	public void removeObserver(MyObserver ob) {
		if (ob == null) return;
		observers.remove(ob);
	}

	public void notifyObservers() {
		if (!changed) return;
		List<MyObserver> snapshot = new ArrayList<MyObserver>(observers);
		for (MyObserver ob : snapshot) {
			try {
				ob.update();
			} catch (Exception ex) {
				// ignore observer exceptions to avoid breaking notification loop
			}
		}
		changed = false;
	}
}