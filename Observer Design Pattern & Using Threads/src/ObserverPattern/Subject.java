package ObserverPattern;

import java.io.IOException;

public interface Subject 
{
	public void add_observer(Observer new_observer);
	
	public void remove_observer(Observer observer);
	
	public void notify_observers() throws IOException;
	
	public void update(int value) throws IOException;
	
	public String get_name();
	
	public int get_value();

}
