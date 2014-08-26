import java.io.*;
import java.util.BitSet;
import sdsu.util.ConvertableBitSet;

public class Filer //extends MainActivity
{
	final int fileSize = 512;
	
	byte[] data = new byte[175];
	private File file;
	
	public Filer(String fileName)
	{
		boolean opened=false;
		while(!opened) {opened=openFile(fileName);}
	}
		
	private boolean openFile(String fileName)
	{
		//Checks if file exists
		file = new File(fileName);
		
		
		if(file.exists())
		{
			IO.log("Data present. Opening file...");
			//Opens and reads file file
			try
			{
				FileInputStream fip = new FileInputStream(file);
				fip.read(this.data);
				fip.close();
			}
			catch(Exception e) {e.printStackTrace();}
			
			IO.log("Data loaded successfully");
			
			return true;
		}
		
		else
		{
			IO.log("Data absent. Creating file");
			
			//Creates new file
			//and handles exception
			try{file.createNewFile();}
			catch(IOException e) {e.printStackTrace();}
			
			//Makes it 175 bytes
			byte[] zeros = new byte[fileSize];
			for(short i=0; i<fileSize; i++)
			{zeros[i]=0;}
			
			try
			{
				FileOutputStream fop = new FileOutputStream(file);
				fop.write(zeros);
				fop.flush();
				fop.close();
			}
			catch(IOException e){e.printStackTrace();}
			
			IO.log("File created successfully.");
			
			return false;
		}
		
	}
	
	public BitSet getSlots()
	{
		BitSet bitSlots = new BitSet(24);
		//TODO: Check if fixed properly (byte i=4; i<7; i++)
		for(byte i=6; i>3; i--)
		{
			BitSet set = byteToBitSet(data[i]);
			
			for(byte index=0; index<7; index++)
			{bitSlots.set((i-4)*8+index, set.get(index));}
		}
		return bitSlots;
	}
	
	public byte getEntriesNumber(BitSet bitSlots)
	{
		byte entriesNumber=0;
		for(byte i=0; i<MainActivity.slots; i++)
		{if(bitSlots.get(i)) {entriesNumber++;}}
		
		return entriesNumber;
	}
	
	public byte[] getEntriesIndex(byte entriesNumber, BitSet bitSlots)
	{
		byte[] entriesIndex = new byte[entriesNumber];
		byte ii=0;
		for(byte i=0; i<entriesNumber; i++)
		{if(bitSlots.get(i)){entriesIndex[ii]=i; ii++;}}
		return entriesIndex;
	}
	
	//TODO: fit entriesIndex in here!
	public String[] getEntriesName(byte entriesNumber, byte[] entriesIndex)
	{
		String[] entriesName = new String[entriesNumber];
		
		for(byte y=0; y<MainActivity.slots; y++)
		{
			byte[] string = new byte[24];
			for(byte x=0; x<23; x++)
			{string[x]=data[7+y*28];}
			try {entriesName[y] = new String(string,"UTF-8");}
			catch (UnsupportedEncodingException e) {e.printStackTrace();}
		}
		return entriesName;
	}
	//TODO: byte inverse order
	public float[] getEntriesPrice(byte entriesNumber, byte[] entriesIndex)
	{
		float[] entriesPrice = new float[entriesNumber];
		
		for(byte z=0; z<entriesNumber; z++)
		{
			ConvertableBitSet bitPrice = new ConvertableBitSet(32);
			for(byte i=0; i<4; i++)
			{
				BitSet set = byteToBitSet(data[i]);
			
				for(byte index=0; index<7; index++)
				{if(set.get(index)){bitPrice.set((i-4)*8+index);}}
			}
				
			entriesPrice[z] = bitPrice.toFloat();
		}
		
		return entriesPrice;
	}
	
	public BitSet byteToBitSet(byte b)
	{
		BitSet bitset = new BitSet(8);  
		for (int i=0; i<8; i++)   
		{if ((b & (1 << i)) > 0) {bitset.set(i);}}  
		
		return bitset;
	}
	
}
