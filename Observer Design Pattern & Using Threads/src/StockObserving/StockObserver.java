package StockObserving;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ObserverPattern.Observer;
import ObserverPattern.Subject;

public class StockObserver implements Observer
{
	private int observer_id;
	private String name;
	private static int id_tracker = 0;
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	
	public StockObserver(String name, ArrayList<Stock> subscribed_stocks)
	{
		this.observer_id = id_tracker++;
		this.name = name;
		
		for ( Stock stock : subscribed_stocks)
		{
			stocks.add(stock);
			stock.add_observer(this);			
		}
	}
	
	@Override
	public void update() throws IOException 
	{
		
		File results = new File("sonuclar.txt");
		FileWriter fw = new FileWriter(results, true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		String output = String.format("%-10s isimli kullanıcının takip ettiği değerler bildiriliyor.", this.name);
		bw.write(output);
		bw.newLine();
		bw.write("---");
		bw.newLine();
		
		for( Stock stock : stocks)
		{
			output = String.format("%-10s : %5d$", stock.get_name(), stock.get_value());
			bw.write(output);
			bw.newLine();
		}
		
		bw.write("-");
		bw.newLine();	
		bw.close();
		fw.close();
	}

}
