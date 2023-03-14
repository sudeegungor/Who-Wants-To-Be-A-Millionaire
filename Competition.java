package obj;

import java.awt.event.KeyEvent;

import main.Game;
import utility.Miscellaneous;
import utility.StrOperations;

public class Competition {

	private Participant participant;
	private Question[] questions;
	private String[] stopWords;
	private Game game;
	private Statistics statistics;
	
	private boolean lifeline1 = true;
	private boolean lifeline2 = true;
	private String log = "";
	
	//Question results
	private static final int RESULT_CORRECT = 1;
	private static final int RESULT_TIMEOUT = -1;
	private static final int RESULT_WRONG= -2;
	private static final int RESULT_EXIT = -3;
	
	private static final int PANEL_X = 60;//X value for the position of the things like timer and lifelines
	
	private static final int[] REWARDS= {0, 20000, 100000, 250000, 500000, 1000000};
	
	public Competition(Participant participant, Question[] questions, String[] stopWords, Game game, Statistics statistics)
	{
		this.participant = participant;
		this.questions = questions;
		this.stopWords = stopWords;
		this.game = game;
		this.statistics = statistics;
	}
	
	public void startCompetition()
	{
		participant.setCompeted(true);
		game.clearScreen();
		game.println("Contestant: " + participant.getName());
		
		int stage;
		int questionResult = RESULT_CORRECT;//I had to define, otherwise the IDE complains
		for(stage = 1; stage <= 5; stage++)
		{
			Question currentQuestion = selectQuestion(stage);
			game.clearScreen();
			questionResult = askQuestion(currentQuestion);
			
			if(questionResult == RESULT_WRONG)
			{
				log += currentQuestion.getId() + "," + participant.getId() + ",wrong" ;
				statistics.updateCategory(currentQuestion, false);
				break;
			}
			if(questionResult == RESULT_TIMEOUT)
			{
				log += currentQuestion.getId() + "," + participant.getId() + ",timeout" ;
				break;
			}
			if(questionResult == RESULT_EXIT)
			{
				log += currentQuestion.getId() + "," + participant.getId() + ",exit" ;
				break;
			}
			
			log += currentQuestion.getId() + "," + participant.getId() + ",correct\n" ;
			statistics.updateCategory(currentQuestion, true);
			game.clearScreen();
		}
		
		//-1 because stage is always correct answers+1
		participant.setCorrectAnswers(stage-1);
		participant.setEarned(determineEarned(stage, questionResult));
		statistics.updateParticipantStats(participant);
		
		game.println("Correct answers: " + participant.getCorrectAnswers());
		game.println("You won $" + participant.getEarned());
	}
	
	private Question selectQuestion(int stage)
	{
		CloudItem[] cloud = generateCloud(20, stage);
		printCloud(cloud);
		
		String selection;
		game.print("\n\n");
		game.print("Enter your selection: ");
		while(true)
		{
			selection = game.readLine();
			
			for(int i = 0; i < cloud.length; i++)
			{
				if(cloud[i].getWord().equalsIgnoreCase(selection))
				{
					return cloud[i].getOrigin();
				}
			}
			
			game.print("Selection couldn't be found in the cloud. Please try again: ");
		}
	}
	
	public CloudItem[] generateCloud(int size, int stage)
	{
		CloudItem[] cloud = new CloudItem[size];
		int tries = 0;
		
		for(int i = 0; i < size;)
		{
			if(tries > size*1000)
				break;
			
			int index = Miscellaneous.randomWithRange(0, questions.length-1);
			if(questions[index].getDifficulty() != stage)
			{
				tries++;
				continue;
			}
			
			String[] words = StrOperations.wordsOfString(questions[index].getText(), stopWords);
			int wIndex = Miscellaneous.randomWithRange(0, words.length-1);
			
			for(int j = 0; j < size; j++)
			{
				if(cloud[j] != null && cloud[j].getWord().equals(words[wIndex]))
				{
					tries++;
					break;
				}
				
				if(cloud[j] == null)
				{
					cloud[j] = new CloudItem(words[wIndex], questions[index]);
					i++;
					break;
				}
			}
		}
		
		return cloud;
	}
	
	//returns 1 on correct answer, -1 for timeout, -2 for wrong answer, -3 for exit, -4 for thread exception
	private int askQuestion(Question question)
	{   
		int correctAnswer = getVK(question.getCorrectAnswer());
		int[] incorrectAnswers = getIncorrectAswers(question.getCorrectAnswer());	
		
		System.out.println("QUESTION: "+question.getDifficulty());
		System.out.println(question.getText());
		
		int choiceY = game.getCursorPosY();
		System.out.println("A: "+question.getChoice1());
		System.out.println("B: "+question.getChoice2());
		System.out.println("C: "+question.getChoice3());
		System.out.println("D: "+question.getChoice4());
		System.out.println("Press E to exit.");
		
		printPanel(question);
		
		long startTime = System.currentTimeMillis();
		int remainingTime;
		boolean doubleDipActive = false;
		game.rkey = 0;
		while(true)
		{
			if(game.isKeyPressed(correctAnswer))
			{
				return RESULT_CORRECT;
			}
			
			if(game.isKeyPressed(incorrectAnswers[0]))
			{
				if(!doubleDipActive)
				{
					game.setCursorPosition(0, 9);
					game.println("Wrong answer!");
					return RESULT_WRONG;
				}
				
				doubleDipActive = false;
				removeAnswer(incorrectAnswers[0], choiceY);
				incorrectAnswers[0] = -1;
			}
			
			if(game.isKeyPressed(incorrectAnswers[1]))
			{
				if(!doubleDipActive)
				{
					game.setCursorPosition(0, 9);
					game.println("Wrong answer!");
					return RESULT_WRONG;
				}
				
				doubleDipActive = false;
				removeAnswer(incorrectAnswers[1], choiceY);
				incorrectAnswers[1] = -1;
			}
			
			if(game.isKeyPressed(incorrectAnswers[2]))
			{
				if(!doubleDipActive)
				{
					game.setCursorPosition(0, 9);
					game.println("Wrong answer!");
					return RESULT_WRONG;
				}
				
				doubleDipActive = false;
				removeAnswer(incorrectAnswers[2], choiceY);
				incorrectAnswers[2] = -1;
			}
			
			if(game.isKeyPressed(KeyEvent.VK_E))
			{
				game.setCursorPosition(0, 9);
				game.println("You exited from the contest!");
				return RESULT_EXIT;
			}
			
			if(game.isKeyPressed(KeyEvent.VK_1) && lifeline1)
			{
				lifeline1 = false;
				lifeline50(question, incorrectAnswers, choiceY);
				printPanel(question);
			}
			
			if(game.isKeyPressed(KeyEvent.VK_2) && lifeline2)
			{
				lifeline2 = false;
				doubleDipActive = true;
				printPanel(question);
			}
			
			remainingTime = calcRemainingTime(startTime, 20);
			printTime(remainingTime);
			
			if(remainingTime == 0)
			{
				game.setCursorPosition(0, 9);
				game.println("Time is up!");
				return RESULT_TIMEOUT;
			}
			
			game.keypr = 0;
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return -4;
			}
		}
	}
	
	private int getVK(String answer) {
		if(answer.equals("A"))
		{
			return KeyEvent.VK_A;
		}
		if(answer.equals("B"))
		{
			return KeyEvent.VK_B;
		}
		if(answer.equals("C"))
		{
			return KeyEvent.VK_C;
		}
		if(answer.equals("D"))
		{
			return KeyEvent.VK_D;
		}
		return 0;
	}
	
	private int[] getIncorrectAswers(String answer) {
		int[] incorrectAnswers = new int[3];
		
		for(int i = 0, j = 0; i < 4; i++)
		{
			if(i == 0 && !answer.equals("A"))
			{
				incorrectAnswers[j] = KeyEvent.VK_A;
				j++;
			}
			
			if(i == 1 && !answer.equals("B"))
			{
				incorrectAnswers[j] = KeyEvent.VK_B;
				j++;
			}
			
			if(i == 2 && !answer.equals("C"))
			{
				incorrectAnswers[j] = KeyEvent.VK_C;
				j++;
			}
			
			if(i == 3 && !answer.equals("D"))
			{
				incorrectAnswers[j] = KeyEvent.VK_D;
				j++;
			}
		}
		
		return incorrectAnswers;
	}
	
	private void lifeline50(Question question, int[] incorrectAnswers, int choiceY)
	{
		int index;
		for(int i = 0; i < 2;)
		{
			index = Miscellaneous.randomWithRange(0, (incorrectAnswers.length-1));
			
			if(incorrectAnswers[index] == KeyEvent.VK_A)
			{
				incorrectAnswers[index] = -1;
				removeAnswer(KeyEvent.VK_A, choiceY);
				i++;
				continue;
			}
			
			if(incorrectAnswers[index] == KeyEvent.VK_B)
			{
				incorrectAnswers[index] = -1;
				removeAnswer(KeyEvent.VK_B, choiceY);
				i++;
				continue;
			}
			
			if(incorrectAnswers[index] == KeyEvent.VK_C)
			{
				incorrectAnswers[index] = -1;
				removeAnswer(KeyEvent.VK_C, choiceY);
				i++;
				continue;
			}
			
			if(incorrectAnswers[index] == KeyEvent.VK_D)
			{
				incorrectAnswers[index] = -1;
				removeAnswer(KeyEvent.VK_D, choiceY);
				i++;
				continue;
			}
		}
	}
	
	private void removeAnswer(int answer, int choiceY)
	{
		if(answer == KeyEvent.VK_A)
		{
			game.setCursorPosition(0, choiceY);
			game.print("A:" + " ".repeat(40));
			return;
		}
		
		if(answer == KeyEvent.VK_B)
		{
			game.setCursorPosition(0, choiceY+1);
			game.print("B:" + " ".repeat(40));
			return;
		}
		
		if(answer == KeyEvent.VK_C)
		{
			game.setCursorPosition(0, choiceY+2);
			game.print("C:" + " ".repeat(40));
			return;
		}
		
		if(answer == KeyEvent.VK_D)
		{
			game.setCursorPosition(0, choiceY+3);
			game.print("D:" + " ".repeat(40));
			return;
		}
	}
	
	private int calcRemainingTime(long startTime, int limit)
	{
		return (limit - (int)((System.currentTimeMillis() - startTime)/1000));
	}
	
	private void printTime(int time)
	{
		game.setCursorPosition(PANEL_X, 4);
		game.print("Remaining Time: ");
		if(time > 9)
		{
			game.print("" + time);
			return;
		}
		game.print("0" + time);
	}
	
	private int determineEarned(int stage, int questionResult)
	{
		if(questionResult == RESULT_WRONG)
		{
			if(stage == 1 || stage == 2)
			{
				return REWARDS[0];
			}
			
			if(stage == 3 || stage == 4)
			{
				return REWARDS[2];
			}
			
			return REWARDS[4];
		}
		
		return REWARDS[stage-1];
	}
	
	public void printCloud(CloudItem[] cloud)
	{
		game.println("Word Cloud:");
		for(int i = 0; i < cloud.length; i++)
		{
			if(cloud[i] == null)
				break;
			
			game.print(" ".repeat(Miscellaneous.randomWithRange(1, 9)) + cloud[i].getWord());
		}
	}
	
	public void printPanel(Question question)
	{
		game.setCursorPosition(PANEL_X, 3);
		game.print("Money: $" + REWARDS[question.getDifficulty()-1]);
		
		game.setCursorPosition(PANEL_X, 5);
		if(lifeline1) game.print("(1) 50%");
		else game.print("-              ");
		
		game.setCursorPosition(PANEL_X, 6);
		if(lifeline2) game.print("(2) Double Dip");
		else game.print("-              ");
	}

	public String getLog() {
		return log.trim();
	}
}
