import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Scanner;

public class PreProcess {

	HashMap<String, Integer> map = new HashMap<String, Integer>();
	BufferedWriter bw = null;
	Random rand = new Random();
	
	String convertToInt(String srcpath) 
	{
		int  n = rand.nextInt(100) + 1;				// generate random number to store an input file with same name an addition of a random no.
		String destpath = srcpath.substring(0, srcpath.length()-4) + n + srcpath.substring(srcpath.length()-4, srcpath.length());
		BufferedReader read = null;
		int counter = 0;
		try
		{
			bw = new BufferedWriter(new FileWriter(destpath));
			read = new BufferedReader(new FileReader(srcpath));
			String Line;
			while (( Line = read.readLine() ) != null)
			{
				Line = Line.replaceAll("/n", "");
				String[] words = Line.split(" ");
				for (String word : words)			// every new string encountered write a new integer to the new file
				{	
					Integer i = map.get(word);
					if (i == null)
					{
						map.put(word, counter);
						i = counter;
						++counter;
					}
					bw.write(i.toString() + " ");	// else write the same number i.e present in the HashMap for corresponding to the String
				}
				bw.newLine();
			}
			bw.close();
			read.close();
		}
	
		catch (Exception e) 
		{
			e.printStackTrace();					// print Exception
		}
		return destpath;
	}
	
	String convertToString(String srcpath, String destpath)
	{
		try
	    {
			String[] line;
			int[] a;
			bw = new BufferedWriter(new FileWriter(destpath));
			Scanner sc = new Scanner(new File(srcpath));
			while (sc.hasNext())
	        {   
				line = sc.nextLine().split(" ");
				a = parseIntArray(line);
				forLoop:
				for(int j=0; j<a.length-1; j++)
				{
					int i = a[j];
					Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
					while(it.hasNext())
					{
						Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
						if(pair.getValue() == i)			// convert the final output integers back to string present in the HashMap
						{	
							bw.write(pair.getKey() + " ");	// write it  in the output file
							continue forLoop;
						}
					}
				}
				bw.write("(" + line[line.length-1] + ")");	// write the count of the frequent itemset
				bw.newLine();
	        }
			sc.close();
			bw.close();
	    }
		
	    catch (Exception e) 
	    {
	        e.printStackTrace();							// print Exception
	    }
		return destpath;
		
	}
															// method to convert String array to integer array
    int[] parseIntArray(String[] ar)
    {
        int[] int_ar = new int[ar.length];
        for (int i = 0; i < int_ar.length; i++)
        {
            int_ar[i] = Integer.parseInt(ar[i]);
        }
        return int_ar;
    }

}