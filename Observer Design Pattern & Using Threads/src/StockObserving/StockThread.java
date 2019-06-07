package StockObserving;


public class StockThread implements Runnable
{
	private int sleep_time;
	private int run_time;
	private Stock stock;
	
	public StockThread(Stock stock, int start_time, int update_count)
	{
		this.stock = stock;
		this.sleep_time = start_time;
		this.run_time = update_count;
	}
	
	public void run() 
	{
		for (int i = 0; i < run_time; i++)
		{
			try
			{
				Thread.sleep(sleep_time*1000);
			}
			catch(InterruptedException e)
			{
				System.out.format("Thread %10s interrupted", "sadas");
			}
			synchronized(this)
			{
				int random_price_change = (int) (Math.random()*100) - 50;
				this.stock.update(random_price_change);				
			}			
		}		
	}
}
