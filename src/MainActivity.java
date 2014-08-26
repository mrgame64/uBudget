import java.util.BitSet;
import java.lang.Math;

public abstract class MainActivity
{
	final static int slots = 17; //Slots to be loaded
	static byte entriesNumber; //Number of full slots
	static byte[] entriesIndex; //
	static String[] entriesName; //Entries' names
	static float[] entriesPrice; //Entries' prices
	static float[] entriesShare; //Money currently allocated to entries
	static float[] entriesPriority; //The priority coefficient
	static float entriesPrioritySum; //The sum of all priority coefficients
	static float budgetAmount; //Total budget money
	static float budgetAllocated; //Available (not allocated) budget money
	
	public static void main(String[] args) 
	{
		IO.log("uBudget alpha");
		
		//Opens a new file
		Filer file = new Filer("data.bin");
		
		//Reads slots
		IO.log("Checking for entries...");
		BitSet bitSlots = file.getSlots();
		//Counts entries
		entriesNumber=file.getEntriesNumber(bitSlots);
		
		
		//Self-explanatory
		if(entriesNumber>0)
		{
			IO.log("Entries present. Loading...");
			
			//Gets entries' indexes
			entriesIndex = file.getEntriesIndex(entriesNumber, bitSlots);
			//Reads entries' names
			entriesName = file.getEntriesName(entriesNumber, entriesIndex);
			//Reads entries' prices
			entriesPrice = file.getEntriesPrice(entriesNumber, entriesIndex);
			//Reads entries' priorities
			
			
			IO.log("Entries loaded.");
		}
		
		else
		{IO.log("[No entries]");}
		
		//Prints budget
		IO.log("");
		IO.log("Budget: "+String.valueOf(budgetAmount));
		IO.log("");
		
		//Allocates money
		//IO.log("Calculating...");
		for(byte i=0; i<entriesNumber; i++)
		{budgetAllocated+=entriesPrice[i];}
		
		//Add addictive priority, based on price
		for(byte i=0; i<entriesNumber; i++)
		{entriesPriority[i]+=priorityFormula(i);}
		
		//Sum of priorities
		for(byte i=0; i<entriesNumber; i++)
		{entriesPrioritySum+=entriesPriority[i];}
		
		//Shares of entries
		for(byte i=0; i<entriesNumber; i++)
		{entriesShare[i]=(budgetAmount/entriesPrioritySum)*entriesPriority[i];}
		
		//Prints all entries
		for(byte i=0; i<entriesNumber; i++)
		{
			System.out.print("\t"+String.valueOf(i+1)+entriesName[i]);
			System.out.println(" - "+String.valueOf(entriesShare[i]));
		}
		
		//Communicates if the budget is fulfilled
		if(budgetAmount>=budgetAllocated)
		{System.out.println("\nBudget fulfilled.\n");}
		
		//Lets the user choose an action
		int answer=IO.ask(0);
		
		byte action=(byte) Math.floor(answer/10);
		//byte element=(byte) (answer-action*10);
		
		switch(action)
		{
		//case 
		}
	}
	
	private static float priorityFormula(byte entry)
	{
		return (entriesPrice[entry]*entriesPriority[entry]-budgetAllocated/entriesNumber)/(entriesNumber);
	}
	
	/*
	private float average(float[] array)
	{
		float average=0;
		int length=array.length;
		for(int i=0; i<length; i++)
		{average+=array[i];}
		
		return average/length;
	}*/
}
