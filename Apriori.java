import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner; 


public class Apriori {
	
	static List<String> myList = new ArrayList<String>();
	static List<String> scanList = new ArrayList<String>();
	static HashMap<String, Integer> map = new HashMap<String, Integer>();
	static int min_sup;
	static int k;

	public static void main(String[] args) 
	{
		PreProcess p = new PreProcess();			
		FreqSet fs = new FreqSet();
		String tmppath = p.convertToInt(args[2]);			// temporary file path that contains string converted to integers
		min_sup = Integer.parseInt(args[0]);				// min support user input
		k = Integer.parseInt(args[1]);						// user input frequent k itemset
		String destpath = args[3];							// destination path to store final output
		Random rand = new Random();
		int  rdom = rand.nextInt(100) + 1;					// random number to generate the file with same name as input file appended the no
		String midop = destpath.substring(0, destpath.length()-4) + rdom + destpath.substring(destpath.length()-4, destpath.length());
		int count = 0;
		
		
		// if min support is less than zero print error message
		if (min_sup <= 0)
		{			
			System.out.println("Minimum support cannot be less than or equal to 0");
			System.exit(0);
		}
		else 
		if (k <= 0)			// if freq k itemset is less than zero print error message
		{
			System.out.println("Frequent k itemset value cannot be less than or equal to 0");
			System.exit(0);
		}
				addElements(tmppath);
				try
				{
					fs.setMinSup(min_sup);				// set min support
					fs.setK(k);							// set value of k
					fs.setHashMap(map);					// set hashmap value 
					fs.setScanList(scanList);			// database scan list
					scanList = null;
					System.out.println(new Date().getTime());		// to check how much time my each method takes
					myList = fs.deleteCandidates(myList);			// delete candidates that do not comply to min support
					System.out.println(new Date().getTime());
					fs.deleteItemsFromScanList();					// remove the candidates removed in previous step which reduces for further steps
					System.out.println(new Date().getTime());
					do
					{
						count++;									// tells the length of candidates you have in myList
						fs.printFreqKPlus(count, midop);			// print freq itemsets
						System.out.println(new Date().getTime());
						myList = fs.candidateGen(myList, count);			// generates candidates
						System.out.println(new Date().getTime());						
						fs.scanDatabase(count);						// scan database for counts of generated candidates
						System.out.println(new Date().getTime());
						myList = fs.deleteCandidates(myList);		// delete candidates that do not comply minimum support
						System.out.println(new Date().getTime());
					}
					while(!(myList.isEmpty()));						// till list is empty
				}
				catch(Exception e)
				{
					e.printStackTrace();							// print Exception
				}
			p.convertToString(midop, destpath);
		}
			
			static void addElements(String path)
			{
					try
			        {	
						Scanner sc = new Scanner(new File(path));
						while (sc.hasNext())
				        {
							String Line = sc.nextLine();
							String[] line = Line.split(" ");
							if(line.length >= k)
							{
								scanList.add(Line);					// add the transaction that has length greater than k 
								for(int i=0; i<line.length; i++)
								{
									Integer n = map.get(line[i]);	// add values to hashMap and store its corresponding counts
									if (n == null)					// if not present add to hashMap with count of 1
									{
										map.put(line[i], 1);
										myList.add(line[i]);
									}
									else if(map.containsKey(line[i]))			// else increment count and put back in HashMap 
										map.put(line[i], map.get(line[i])+1); 
								}
							}
						}
						sc.close();
			        }
					catch(Exception e)
					{
						e.printStackTrace();						// print Exception
					}
			}
}