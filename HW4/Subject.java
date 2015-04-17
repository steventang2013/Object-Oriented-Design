import java.util.ArrayList;
import java.util.List;

public class Subject{
	
	protected List<Observer> observers;
	
	public Subject(){
		observers = new ArrayList<Observer>();
	}
	
	public void addObserver(Observer o){
		observers.add(o);
	}
	
	public void notifyObs(double[] params){
		for (Observer o : observers)
			o.update(params);
	}
}
