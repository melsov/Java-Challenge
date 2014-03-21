package sorting;

import static java.lang.System.out;

public class DoSomeSorting {

	int array_size = 100000;
	private int[] original_nums = new int[array_size];
//	private int[] nums = new int[array_size];
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
		for(int i = 0; i < original_nums.length; ++i) 
		{
			original_nums[i] = (int)(Math.random() * original_nums.length);
		}
	}
	
	private int[] copyOrigToNums()
	{
		int[] nums = new int[original_nums.length];
		for(int i = 0; i < nums.length; ++i)
		{
			nums[i] = original_nums[i];
		}
		return nums;
	}
	
	public void doBubbleSortAndReportTime()
	{
		int[] nums = copyOrigToNums();
		logNums(nums); // show the mixed up numbers
		
		// bubble sort
		long start = System.nanoTime();
		nums = bubbleSort(nums);
		long end = System.nanoTime();
		
		logHowLong(end - start, "bubble");
		logNums(nums);
		
		//insertion sort
		nums = copyOrigToNums();
		
		start = System.nanoTime();
		nums = insertionSort(nums);
		end = System.nanoTime();
		
		logHowLong(end - start, "insertion");
		logNums(nums);
		
		nums = copyOrigToNums();
		
		start = System.nanoTime();
		nums = quickSort(nums);
		end = System.nanoTime();
		
		logHowLong(end - start, "quick");
		
		logNums(nums);
	}
	
	private int[] insertionSort(int[] data)
	{
		  int len = data.length;
		  int key = 0;
		  int i = 0;
		  for(int j = 1;j<len;j++)
		  {
			  key = data[j];
			  i = j-1;
			  while(i>=0 && data[i]>key)
			  {
				  data[i+1] = data[i];
				  i = i-1;
				  data[i+1]=key;
			  }
		  }
		  return data;
		}
	
	private int[] bubbleSort(int[] nums)
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
		return nums;
	}
	
	public int[] quickSort(int[] data)
	{
		int lenD = data.length;
		int pivot = 0;
		int ind = lenD/2;
		int i,j = 0,k = 0;
		
		//IS THIS AN ARRAY WITH JUST ONE ELEMENT?
		// IF SO, RETURN IT
		if(lenD<2)
		{
			return data;
		}
		else
		{
			//ARRAY HAS MORE THAN ONE ELEMENT
			//MAKE TWO NEW ARRAYS: LEFT AND RIGHT
			int[] L = new int[lenD];
			int[] R = new int[lenD];
			
			//THIRD ARRAY TO USE LATER
			int[] sorted = new int[lenD];
			
			//AND CHOOSE SOME NUMBER FROM THE ORIG ARRAY
			pivot = data[ind];
			
			//AND PUT ALL ELEMENTS LESS THAN PIVOT INTO "LEFT"
			// AND GREATER THAN PIVOT INTO RIGHT...
			// AND KEEP TRACK OF HOW MANY WENT INTO EACH (WITH j and k)
			for(i=0;i<lenD;i++)
			{
				if(i!=ind)
				{
					if(data[i]<pivot)
					{
						L[j] = data[i];
						j++;
					}
					else
					{
						R[k] = data[i];
						k++;	
					}	
				}
			}
			
			//TWO NEW ARRAYS TO HOLD L AND R (OF LENGTH J AND K RESPECTIVELY)
			int[] sortedL = new int[j];
			int[] sortedR = new int[k];
			System.arraycopy(L, 0, sortedL, 0, j);
			System.arraycopy(R, 0, sortedR, 0, k);
			
			//THE OUTRAGEOUS PART
			//SIMPLY CALL THE SAME FUNCTION ON THESE TWO NEW ARRAYS
			sortedL = quickSort(sortedL);
			sortedR = quickSort(sortedR);
			
			//THEN COPY THEM INTO THE 'SORTED' ARRAY (THE FINAL PRODUCT FOR THIS FUNC.)
			System.arraycopy(sortedL, 0, sorted, 0, j);
			sorted[j] = pivot; // WE NEVER PUT THE PIVOT NUMBER INTO THE RESULT SO DO THAT NOW
			System.arraycopy(sortedR, 0, sorted, j+1, k);
			
			//ALL DONE
			return sorted;
		}
	}
	
	private void logNums(int[] nums)
	{
		return;
//		for(int i: nums)
//		{
//			out.print(i + ", ");
//		}
//		out.println();
	}
	
	
	private void logHowLong(long duration, String sort_name) 
	{
		out.println(sort_name + " sort took: " + duration + " nanoseconds" );
		out.println(" or : " + (duration/1000000d ) + " milliseconds");
	}
	
}
