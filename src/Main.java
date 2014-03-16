import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;

import javax.swing.text.Document;


public class Main {

	/**
	 * Лабораторная работа №1
	 * Автор: Точилина С.К.
	 * Работа с базовыми классами стандартных пакетов 
	 * java.io, java.net, java.util
	 * Необходимо получить содержимое web странички, 
	 * проиндексировать слова в нем
	 * и результаты сохранить в файл
	 *  Программа должна быть исполнена в 
	 *  виде консольного приложения,
	 *  позволяющего выбирать адрес web страницы и имя файла,
	 *   в который будут сохранены результаты	
	 * @param args
	 */
	public static void main(String[] args) {
		String bodyText=null;
		String pageText = null;
		String url="http://example.com/";		
		System.out.println("Введите адрес");
		BufferedReader input=null;
		try {
		input = new BufferedReader(new InputStreamReader(System.in,"cp866"));
					
			 url=input.readLine();
		} catch (IOException e) {
			System.err.println("Input url error: "+ e);
		}  
		System.out.println("Введите имя файла");
		  String fileName2="file.txt";
		try {			
			 fileName2=input.readLine();
		} catch (IOException e) {
			System.err.println("Input file name error: "+ e);
		}  
		
		try{
		URL myUrl  = new URL(url);
	    String fileName = "result_html.html";
	    String fileName3=fileName2+".txt";
	    PrintWriter outfile = WriteFile(fileName) ;
	    try {		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(myUrl.openStream()));
		String line = null;			
		while((line = br.readLine())!=null)
			{
			outfile.println(line);
			pageText+= line + "\r\n";	
			}
		br.close();
		org.jsoup.nodes.Document htmlPage =  Jsoup.parse(pageText);
		 bodyText = ((org.jsoup.nodes.Document) htmlPage).body().text();
		 String[] words = bodyText.split(" ");
			HashMap<String, Integer> index2 =  new HashMap<String, Integer>();
		 index2 = FindIndex(words,index2);
		 IndexWriter(index2,fileName3);
		}
		 finally {				 
			outfile.close();
				}
		}
		catch(MalformedURLException me){
		System.err.println("Unnoen host: "+ me);
		System.exit(0);
		}
		catch (IOException ioe) {
			System.err.println("Input error: "+ ioe);	
		}
	}
public static HashMap<String, Integer> FindIndex(String[] words,HashMap<String, Integer> wordToCount )
{ 
 for (String word : words)
{
	if (word.length()>=2)
	{
		if (!wordToCount.containsKey(word))
		{
			wordToCount.put(word, new Integer(1));
		}
		else 
		{
			int current = wordToCount.get(word)+1;
			wordToCount.remove(word);
			wordToCount.put(word, current);
		}
	}
}
 return wordToCount;
}

public static void IndexWriter(HashMap<String, Integer> wordToCount,String fileName  ) 
{
	PrintWriter file= WriteFile(fileName);
	for (Entry<String, Integer> entry : wordToCount.entrySet())
	{
		file.println(entry.getKey()+"   "+entry.getValue());		
	}
		
		 file.close();
	
}
public static PrintWriter WriteFile( String fileName ) 
{
	try {
	File file = new File(fileName);
	if(!file.exists()){
		file.createNewFile();
		}
	PrintWriter outfile = new PrintWriter(file.getAbsoluteFile());
	return outfile;
	}
	catch (IOException ioe) {
		System.err.println("File error: "+ ioe);	
		return null;
	}
}
}
