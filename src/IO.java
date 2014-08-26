import java.util.Scanner;

public abstract class IO extends MainActivity
{
	private static Scanner input = new Scanner(System.in);

	
	public static int ask(int mode)
	{
		int answer=-1;
		
		switch(mode)
		{
		case 0:
			log("Choose an action:");
			log("\t 0. Change budget amount");
			log("\t 1. Exit program");
			log("\t 2. Add entry");
			log("\t 3. Purchase entry");
			log("\t 4. Cancel entry");
		
			int choice=input.nextInt();
			
			if(choice>1)
			{
				answer=choice*10;
				log("");
				log("Which one?");
				answer+=input.nextInt();
			}
			else
			{
				if(choice<1)
				{
					log("Input amount:");
					//float amount = input.nextFloat();
				}
				else
				{
					log("Saving data...");
				}
			}
		}
		
		return answer;
	}
	
	public static void log(String string)
	{System.out.println(string);}
}
