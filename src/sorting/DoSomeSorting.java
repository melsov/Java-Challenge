package sorting;

import static java.lang.System.out;

public class DoSomeSorting {

	int array_size = 1000;
	private int[] original_nums = new int[array_size];
	private int[] nums = new int[array_size];
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException 
	{
		// TODO Auto-generated method stub
		DoSomeSorting dss = new DoSomeSorting();
		dss.doBubbleSortAndReportTime();
	}
	
	public DoSomeSorting()
	{
		setUpOriginalNums();
	}
	
	private void setUpOriginalNums()
	{
		for(int i = 0; i < nums.length; ++i)
		{
			original_nums[i] = (int)(Math.random() * nums.length);
		}
	}
	
	private void copyOrigToNums()
	{
		for(int i = 0; i < nums.length; ++i)
		{
			nums[i] = original_nums[i];
		}
	}
	
	public void doBubbleSortAndReportTime()
	{
		copyOrigToNums();
		logNums();
		long start = System.nanoTime();
		bubbleSort();
		long end = System.nanoTime();
		long duration = end - start;
		out.println("bubble sort took: " + duration + " nanoseconds" );
		out.println(" or : " + (duration/1000000d ) + " milliseconds");
		
		logNums();
	}
	
	private void bubbleSort()
	{
		for(int i = 1; i < nums.length; ++i)
		{
			for(int k = i; k > 0; --k)
			{
				if (nums[k] < nums[k - 1])
				{
					break;
				}
				int temp = nums[k - 1];
				nums[k - 1] = nums[k];
				nums[k] = temp;
			}
		}
	}
	
	private void logNums()
	{
		for(int i: nums)
		{
			out.print(i + ", ");
		}
		out.println();
	}
	
}
