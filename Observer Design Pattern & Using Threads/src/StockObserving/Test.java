package StockObserving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test
{
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		System.out.println("Programda çalışan threadlerin hepsi eşit süreli(1s) sleep komutunu kullanmaktadır.");
		System.out.println("Programda çalışan threadlerin hepsi eşit sayıda(20) rastgele güncelleme yapmaktadır.");
		
		Stock[] stocks = create_stocks();
		StockObserver[] observers = create_observers(stocks);	
		file_setup();			
		run_threads(stocks, 1, 20);
	}
	
	public static Stock[] create_stocks()
	{
		Stock A = new Stock("NYSE", 200);
		Stock B = new Stock("NASDAQ", 300);
		Stock C = new Stock("JPX", 250);
		Stock D = new Stock("Euronext", 150);
		Stock E = new Stock("LSE", 500);
		
		Stock[] stocks = {A, B, C, D, E};
		
		return stocks;
	}
	
	public static StockObserver[] create_observers(Stock[] stocks)
	{
		ArrayList<Stock> stocks_list = new ArrayList<Stock>();
		
		/*1. gözlemcinin oluşturulması*/		
		stocks_list.add(stocks[0]);
		stocks_list.add(stocks[1]);
		stocks_list.add(stocks[2]);
		
		StockObserver John = new StockObserver("John", stocks_list);
		stocks_list.clear();
		
		/*2. gözlemcinin oluşturulması*/
		stocks_list.add(stocks[1]);
		stocks_list.add(stocks[4]);
		
		StockObserver Reese = new StockObserver("Reese", stocks_list);
		stocks_list.clear();
		
		/*3. gözlemcinin oluşturulması*/
		stocks_list.add(stocks[3]);
		
		StockObserver Bruce = new StockObserver("Bruce", stocks_list);
		stocks_list.clear();
		
		/*4. gözlemcinin oluşturulması*/
		stocks_list.add(stocks[0]);
		stocks_list.add(stocks[1]);
		stocks_list.add(stocks[4]);
		
		StockObserver Alfred = new StockObserver("Alfred", stocks_list);
		stocks_list.clear();
		
		/*5. gözlemcinin oluşturulması*/
		stocks_list.add(stocks[3]);
		stocks_list.add(stocks[4]);
		
		StockObserver Gabe = new StockObserver("Gabe", stocks_list);
		stocks_list.clear();
		
		StockObserver[] observers = {John, Reese, Bruce, Alfred, Gabe};
		
		return observers;
	}
	
	public static void file_setup() throws FileNotFoundException, IOException
	{
		File results = new File("sonuclar.txt");

		if (results.createNewFile())/*Bu isimde dosya yok ise*/
		{
			PrintWriter writer = new PrintWriter(results.getAbsoluteFile());
		    writer.println("Observer Design Pattern & Using Threads");
		    writer.println("---");
		    writer.close();
			System.out.println("Proje klasöründe 'sonuclar.txt' dosyası oluşturuldu.");
			System.out.println("'sonuclar.txt' dosyasına veriler giriliyor.");
		} 
		else/*Bu isimde dosya var ise*/
		{
		    System.out.println("Proje klasöründe 'sonuclar.txt' dosyası bulundu.");
		    PrintWriter writer = new PrintWriter(results.getAbsoluteFile());
		    writer.println("Observer Design Pattern & Using Threads");
		    writer.println("---");
		    writer.close();
		    System.out.println("'sonuclar.txt' dosyasının içeriği silindi.");
		    System.out.println("'sonuclar.txt' dosyasına veriler giriliyor.");
		}
	}

	public static void run_threads(Stock[] stocks, int update_frequency, int update_count)
	{
		Thread NYSE		 = new Thread(new StockThread(stocks[0], update_frequency, update_count));
		Thread NASDAQ	 = new Thread(new StockThread(stocks[1], update_frequency, update_count));
		Thread JPX		 = new Thread(new StockThread(stocks[2], update_frequency, update_count));
		Thread Euronext	 = new Thread(new StockThread(stocks[3], update_frequency, update_count));
		Thread LSE 	   	 = new Thread(new StockThread(stocks[4], update_frequency, update_count));
		
		ExecutorService executor = Executors.newCachedThreadPool();

		executor.execute(NYSE);
		executor.execute(NASDAQ);
		executor.execute(JPX);
		executor.execute(Euronext);
		executor.execute(LSE);
		
		executor.shutdown();
		
		try 
		{
			boolean task_status = executor.awaitTermination(1, TimeUnit.MINUTES);
			
			if(task_status)
			{
				System.out.println("Threads are finished.");
			}
			else
			{
				System.out.println("Timed out.");
			}
	    } 
		catch ( Exception e) 
		{
			System.out.println("Threads interrupted.");
	    }
		
		System.out.println("Dosyaya veri girişi sonlandı.");
		System.out.println("Program sonlandı.");
	}
}
