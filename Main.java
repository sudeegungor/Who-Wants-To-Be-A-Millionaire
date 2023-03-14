package main;
import obj.Category;
import obj.Competition;
import obj.Participant;
import obj.Question;
import obj.Statistics;
import utility.*;

import java.awt.event.KeyEvent;
import java.io.File;

public class Main {

	static final String MENU_STRING = "***** Menu *****\r\n"
			+ "1.Load questions\r\n"
			+ "2.Load participants\r\n"
			+ "3.Start competition \r\n"
			+ "4.Show statistics\r\n"
			+ "5.Exit\r\n"
			+ "\r\n"
			+ "> Enter your choice: ";
	
	static final String TITLE = "Who Wants to Be a Millionaire";
	
	public static void main(String[] args)
	{
		Game game;
		try {
			game = new Game();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		game.setTitle(TITLE);
		
		String[] dic = FileOperations.getDictionary("dictionary.txt");
		String[] stop = FileOperations.getStopWords("stop_words.txt");
		Question[] questions = null;
		Participant[] participants = null;
		Category[] categories = null;
		int[] difficultyNums = new int[5];
		Statistics statistics = new Statistics();
		
		game.clearScreen();
		
        String choice;
        boolean printFlag = true;
        while (true)
        {
        	if(printFlag)
        	{
        		game.clearScreen();
        		System.out.print(MENU_STRING);
        	}
        	
			choice = game.readLine();
			switch (choice) {
			case "1": 
				String questionPath;
				File fileQuestion;
	    		while(true)
	    		{
	    			System.out.print("Enter file name to load:");
	    			questionPath = game.readLine();
			    	
	    			fileQuestion = new File(questionPath);
		    		if (fileQuestion.exists())
		    		{
		    			System.out.println();
		    			questions = FileOperations.getQuestions(fileQuestion, dic, game);
			    		break;
		    		}
		    		System.out.println("Invalid file name.");
	    		}
		    	if(questions == null)
		    	{
		    		System.out.println("Something went wrong while getting the questions.");
		    		System.out.println("Press any key to return to the main menu...");
		    		try {
						game.readKey();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		break;
		   		}
		    	
	    		categories = Miscellaneous.createCategories(questions);
	    		
	    		if(categories == null)
	    		{
		    		System.out.println("Something went wrong while getting the questions.");
		    		System.out.println("Press any key to return to the main menu...");
		    		try {
						game.readKey();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		break;
		   		}
	    	
	    		for(int i = 0; i < questions.length; i++)
	    		{
	    			difficultyNums[(questions[i].getDifficulty()-1)] += 1;
	    		}
	    		
	    		System.out.println("File has been loaded.\n");
	    		
	    		System.out.println("Category                 Number of questions");
	    		for(int i = 0; i < categories.length; i++)
	    		{
	    			System.out.println(categories[i].getName() + " ".repeat(25 - categories[i].getName().length()) + categories[i].getQuestionAmount());
	    		}
	    		System.out.println();
	    		
	    		System.out.println("Difficulty               Number of questions");
	    		for(int i = 0; i < difficultyNums.length; i++)
	    		{
	    			System.out.println((i+1) + " ".repeat(24) + difficultyNums[i]);
	    		}
	    		System.out.println();
	    		
	    		statistics.setCategories(categories);
	    		System.out.println("Press any key to return to the main menu...");
	    		try {
					game.readKey();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    		
	    		printFlag = true;
				break;
			case "2":
				String participantPath;
				File fileParticipant;
	    		while(true)
	    		{
	    			System.out.print("Enter file name to load:");
	    			participantPath = game.readLine();
			    	
	    			fileParticipant = new File(participantPath);
		    		if (fileParticipant.exists())
		    		{
		    			participants = FileOperations.getParticipants(fileParticipant);
		    			System.out.println();
			    		break;
		    		}
		    		System.out.println("Invalid file name.");
	    		}
		    	if(participants == null)
		    	{
		    		System.out.println("Something went wrong while getting the participants.");
		    		System.out.println("Press any key to return to the main menu...");
		    		try {
						game.readKey();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		break;
		    	}

		    	statistics.setParticipants(participants);
		    	
	    		System.out.println("File has been loaded.");
		    	System.out.println("Press any key to return to the main menu...");
	    		try {
					game.readKey();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    		
		    	printFlag = true;
				break;
			case "3":
				if(questions == null || participants == null)
				{
					System.out.println("Questions or participants are not loaded yet.");
					System.out.println("Press any key to return to the main menu...");
		    		try {
						game.readKey();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
				
				String log = "";
				while(true)
				{
					boolean continueFlag = true;
					Participant participant = Miscellaneous.randomParticipant(participants);
					
					if(participant == null)
					{
						System.out.println("All participants have competed.");
						System.out.println("Press any key to return to the main menu...");
			    		try {
							game.readKey();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						break;
					}
					
					Competition competition = new Competition(participant, questions, stop, game, statistics);
					competition.startCompetition();
					log += competition.getLog();
					
					game.println("Next contestant(Y/N)");
					while(true)
					{
						char key;
						try {
							key = game.readKey();
						} catch (InterruptedException e) {
							e.printStackTrace();
							return;
						}
						
						if(key == KeyEvent.VK_Y)
						{
							log += "\n";
							break;
						}
						
						if(key == KeyEvent.VK_N)
						{
							continueFlag = false;
							break;
						}
					}
					
					if(!continueFlag)
						break;
				}
				
				FileOperations.saveLog(log);
				game.clearScreen();
				printFlag = true;
				break;
			case "4":
				if(statistics.getSuccessfulParticipant() == null)
				{
					System.out.println("No one has competed yet.");
					System.out.println("Press any key to return to the main menu...");
		    		try {
						game.readKey();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    		printFlag = true;
		    		break;
				}
				
				game.println("The most successful contestant: " + statistics.getSuccessfulParticipant().getName());
				game.println("The category with the most correctly answered: " + statistics.getBestCategory().getName());
				game.println("The category with the most badly answered: " + statistics.getWorstCategory().getName());
				game.println("Average correct answers for age<=30: " + statistics.getAge1Average());
				game.println("Average correct answers for 30<age<=50: " + statistics.getAge2Average());
				game.println("Average correct answers for 50<age: " + statistics.getAge3Average());
				game.println("The city with the highest number of participants: " + statistics.getHighestParticipantsCity());
				game.println("Press any key to return to the main menu...");
				try {
					game.readKey();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				printFlag = true;
				break;
			case "5":
				game.println("BYE!");
				return;
			default:
				System.out.print("Invalid input. Please try again: ");
				printFlag = false;
				break;
			}
        }
	}
	
}