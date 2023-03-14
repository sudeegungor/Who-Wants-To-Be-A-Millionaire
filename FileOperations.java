package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import main.Game;
import obj.Participant;
import obj.Question;

public class FileOperations {

	public static String[] getDictionary(String dicPath)
	{
		File dicFile = new File(dicPath);
		
		if(!dicFile.exists())
		{
			System.out.println("dictionary not found");
			return null;
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(dicFile));
			String line;
			int counter = 0;
			
			System.out.println("Loading dictionary");
    	    while((line = br.readLine()) != null) {
    	    	counter++;
    	    }
    	    br.close();
    	    
    	    String[] dic = new String[counter];
    	    
    	    BufferedReader _br = new BufferedReader(new FileReader(dicFile));
    	    for(int i = 0;(line = _br.readLine()) != null; i++) {
    	    	dic[i] = line;
    	    }
    	    _br.close();
    	    
			System.out.println("Sorting dictionary");
			Arrays.sort(dic);
    	    
    	    return dic;
		} catch (Exception e) {
			System.out.println("An error occured: " + e.toString());
			return null;
		}
	}
	
	public static String[] getStopWords(String swPath)
	{
		File swFile = new File(swPath);
		
		if(!swFile.exists())
		{
			System.out.println("stop words file not found");
			return null;
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(swFile));
			String line;
			int counter = 0;
			
			System.out.println("Loading stop words");
    	    while((line = br.readLine()) != null) {
    	    	counter++;
    	    }
    	    br.close();
    	    
    	    String[] sw = new String[counter];
    	    
    	    BufferedReader _br = new BufferedReader(new FileReader(swFile));
    	    for(int i = 0;(line = _br.readLine()) != null; i++) {
    	    	sw[i] = line;
    	    }
    	    _br.close();
    	    
			System.out.println("Sorting stop words");
			Arrays.sort(sw);
    	    
    	    return sw;
		} catch (Exception e) {
			System.out.println("An error occured: " + e.toString());
			return null;
		}
	}
	
	public static Question[] getQuestions(File questionFile, String[] dic, Game game)
	{
		try
    	{
			Question[] questions = new Question[0];
	    	BufferedReader br = new BufferedReader(new FileReader(questionFile));
    		String line;
    		String[] elements;
    		Question tempQuestion;
    	    for(int i = 0; (line = br.readLine()) != null; i++) {
    	    	elements = line.split("#");
    	    	elements[1] = StrOperations.spellCorrector(elements[1], dic, game);
    	    	tempQuestion = new Question(elements[0].trim(), elements[1].trim(), elements[2].trim(), elements[3].trim(), elements[4].trim(), elements[5].trim(), elements[6].trim(), Integer.parseInt(elements[7].trim()), i);
    	    	questions = ArrayRealloc.questionArrRealloc(questions, tempQuestion);
    	    }
    	    br.close();
    	    
    	    return questions;
    	}
    	catch(Exception e)
    	{
    		System.out.println("An error occured: " + e.toString());
    		return null;
    	}
	}
	
	public static Participant[] getParticipants(File participantFile)
	{
		try
    	{
			Participant[] participants = new Participant[0];
	    	BufferedReader br = new BufferedReader(new FileReader(participantFile));
    		String line;
    		String[] elements;
    		Participant tempParticipant;
    	    for(int i = 0; (line = br.readLine()) != null; i++) {
    	    	elements = line.split("#");
    	    	tempParticipant  = new Participant(elements[0].trim(), elements[1].trim(), elements[2].trim(), elements[3].trim(), i);
    	    	participants = ArrayRealloc.participantArrRealloc(participants, tempParticipant );
    	    }
    	    br.close();
    	    
    	    return participants;
    	}
    	catch(Exception e)
    	{
    		System.out.println("An error occured: " + e.toString());
    		return null;
    	}
	}
	
	public static void saveLog(String log)
	{
		boolean isNew = !(new File("history.txt").exists());
		log = log.strip();//there sometimes can be "line feed" at the end of the string
		
		try(FileWriter fw = new FileWriter("history.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
				if(!isNew)
					out.println();
					
			    out.print(log);
			} catch (IOException e) {
				System.out.println("An error occured: " + e.toString());
			}
	}
	
}
