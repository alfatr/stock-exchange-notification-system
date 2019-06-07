package StockObserving;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ObserverPattern.Observer;
import ObserverPattern.Subject;

public class Stock implements Subject {
		
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private String name;
	private int current_stock_price;
	private int update_count = 0;
	
	public Stock(String name, int starting_price)
	{
		this.name = name;
		this.current_stock_price = starting_price;
	}
	@Override
	public void add_observer(Observer new_observer) 
	{
		observers.add(new_observer);
	}

	@Override
	public void remove_observer(Observer observer) 
	{
		observers.remove(observers.indexOf(observer));
	}

	@Override
	public void notify_observers() throws IOException 
	{
		for (Observer observer : observers)
		{
			observer.update();
		}
	}

	@Override
	public synchronized void update(int price_change)
	{
		this.current_stock_price = current_stock_price + price_change;
		this.update_count++;
		
		File results = new File("sonuclar.txt");
		
		try 
		{
			FileWriter fw = new FileWriter(results, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String output = String.format("%-10s isimli stoktaki hisse senedi değerleri %3d$ kadar değişti[%2d. güncelleme]", this.name, price_change, this.update_count);
			bw.write(output);
			bw.newLine();
			bw.write("***");
			bw.newLine();	
			bw.close();
			fw.close();	
			
			notify_observers();	
		} 
		catch (IOException e) 
		{
			
		}
		
		
			
	}
	
	public String get_name()
	{
		return this.name;		
	}

	@Override
	public int get_value() 
	{
		return this.current_stock_price;
	}
}
