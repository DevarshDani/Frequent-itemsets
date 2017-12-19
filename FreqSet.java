import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FreqSet {

	int min_sup;
	int k;
	List<String> Fk;
	List<String> scanList;
	HashMap<String, Integer> hm = new HashMap<String, Integer>();
	
	void setScanList(List<String> scanList)
	{
		this.scanList = new ArrayList<String>(scanList);	// set the scanList (database) from the Apriori.java
	}
	void setHashMap(HashMap<String, Integer> m)
	{
		hm.putAll(m);										// set the Hashmap (count) from the Apriori.java
	}
	void setMinSup(int min)
	{
		min_sup = min;										// set the min_sup from the Apriori.java
	}
	void setK(int K) 
	{
		k = K;												// set k from the Apriori.java
	}
	
	List<String> candidateGen(List<String> F1, int count)				
	{

//		tp = new ArrayList<String>();
		String x = "";
								// sort the list before candidate generation

//			tp.add(newS);
//		F1 = new ArrayList<String>(tp);
		Fk = null;
		LinkedList<String> LL = new LinkedList<String>();
//		if(F1.size() > 0)
//		{
		big:
		for(int i=0; i<F1.size()-1; i++)					// from 1st value in list to 2nd last value
		{
				smallFor:
				for(int j=i+1; j<F1.size(); j++)			// from 2nd value to last value
				{
					
//					if(F1.get(i).compareTo(F1.get(j)) < 0)	// F1.get(i) < F1.get(j) then start again with next value
//					{
						if(compareString(F1.get(i),F1.get(j)))	// check whether all elements are equal in them except last
						{
							x = new String(F1.get(i));
							String[] b = F1.get(j).split(" ");
							x = x.concat(" " + b[b.length-1]);
							b = null;
//							String[] p = x.split(" ");
//							if(p.length == 2)					// generate candidate if there are only 1 itemset because there is nothing to match 
//								LL.add(x);						// and all of them are frequent singly
							if(prune(x))					// pruning condition checked in this method if true we do not add to list
									continue smallFor;
							else
								LL.add(x);
						}
//					}
					else
						continue big;
				}
			}
		Fk = new ArrayList<String>(LL);
		return Fk;
		
	}

	HashMap<String, Integer> scanDatabase(int count)
	{
		try
		{
			hm.clear();
			for(int z=0; z<scanList.size(); z++)
			{
				String s = scanList.get(z);									// each transaction in database
				String[] words = s.split(" ");
				if(words.length >= count)									// length should be greater than freq itemset we are looking for
				{
					bigFor:
					for(int i=0; i<Fk.size(); i++)							// for each candidates present
					{
						String[] p = Fk.get(i).split(" ");					// look if it is present in the transaction
						for(int j=0; j<p.length; j++)
						{
							String m = " " + p[j] + " ";
							if(s.contains(m) || p[j].equals(words[0]) || p[j].equals(words[words.length-1]))	// whether transaction has 
								continue;																		// individual item present
							else
								continue bigFor;
						}
								Integer n = hm.get(Fk.get(i));				// if it has all element add it to the HashMap
								n = (n == null) ? 1 : ++n;					// if not present add with value 1
								hm.put(Fk.get(i), n);						// else add with incremented value
					}
				}
				else
					scanList.remove(z);										// remove if length is not greater than count
			}
		}	
		catch(Exception e)
		{
			e.printStackTrace();											// print Exception
		}
		return hm;
	}
	
	List<String> deleteCandidates(List<String> F)
	{
		Iterator<Entry<String, Integer>> it = hm.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String, Integer> entry = it.next();
			if(entry.getValue() < min_sup)									// delete candidates that has frequency less than min support
			{
				it.remove();												// from HashMap as well as
				F.remove(entry.getKey());									// from the List of candidates
			}
		}
		it = null;
		return F;
	}
    
	List<String> deleteItemsFromScanList()
	{
		boolean flag = false;
		LinkedList<String> LL = new LinkedList<String>();
		for(int i=0; i<scanList.size(); i++)								// for each transaction in database
		{
			flag = false;
			String s = scanList.get(i);
			String[] p = s.split(" ");
			for(int j=0;j<p.length; j++)									// for each item in transaction
			{
				if(!(hm.containsKey(p[j])))									// if hashmap does not contain the item
				{
					flag = true;
					if(j == 0)
						s = s.replace(p[j] + " ", "");						// remove the item from the all transaction
					else
						s = s.replace(" " + p[j], "");						// present in the database
				}
			}
			if(flag == true)
			{
				if(!(s.isEmpty()))
					LL.add(s);
			}
			else
				LL.add(s);
		} 
		scanList = new ArrayList<String>(LL);
		return scanList;
	}
	
	boolean prune(String s)													// checks whether the generated candidates 
	{
		String m = "";
		String n = "";
		String o = "";
		boolean flag = false;												// all k-1 subset items are frequent 
		String[] p = s.split(" ");
		for(int z=0; z<p.length-2;z++)
		{
			m = " " + p[z] + " ";
			n = " " + p[z];
			o = p[z] + " ";
			if(s.contains(m))
			{
				s = s.replace(p[z], "");
				s = s.replace("  ", " ");
				s = s.trim();
			}
			else if(s.contains(n))
				{
					s = s.replace(n, "");
					s = s.replace("  ", " ");
					s = s.trim();
				}
			else if(s.contains(o))
				{
					s = s.replace(o, "");
					s = s.replace("  ", " ");
					s = s.trim();
				}
			
			if(!(hm.containsKey(s)))										// if not then 
			{
				flag = true;												// need to prune it
				break;
			}
			else if(hm.get(s) <= min_sup)
			{
				flag = true;
				break;
			}
		}
		if(flag == true)
			return true;
		else
			return false;													// else do not prune it
	}
	
    boolean compareString(String s1, String s2)
    {
    	String p = "";
    	String q = "";
    	String[] S1 = s1.split(" ");
    	String[] S2 = s2.split(" ");
    		p = s1.replace(S1[S1.length-1],"");								// compare whether the string are equal so that 
    		p = p.trim();
    		q = s2.replace(S2[S2.length-1],"");								// candidates can be generated
    		q = q.trim();
    	if(p.equals(q))
    		return true;
    	else
    		return false;
    }
    
	void printFreqKPlus(int count, String path) throws IOException
	{
		PrintWriter pw = new PrintWriter(new FileWriter(path, true));
			try
			{
				if(count >= k)												// if k itemset is greater or equal to k print it in file
				{
					Iterator<Entry<String, Integer>> it = hm.entrySet().iterator();
					while (it.hasNext()) 
					{
						HashMap.Entry<String,Integer> pair = (HashMap.Entry<String,Integer>)it.next();
						pw.print(pair.getKey());							// write key corresponding to all values present in HashMap
						pw.print(" ");
						pw.println(pair.getValue().toString());				// print the count of it permanently without brackets
					}
				}
			}
			catch(Exception E)
			{
				E.printStackTrace();										// print Exception
			}
		pw.close();
	}
}