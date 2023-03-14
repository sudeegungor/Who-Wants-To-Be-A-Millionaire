package utility;

import java.awt.event.KeyEvent;

import main.Game;

public class StrOperations {

	private static final String SPELLCORRECTOR_CHOICE3 = "3-Keep the word";
	private static final String SPELLCORRECTOR_CHOICE2 = "2-Find next";
	private static final String SPELLCORRECTOR_CHOICE1 = "1-Change";

	public static String spellCorrector(String sentence, String[] dictionary, Game game)
	{
		String[] elements = sentenceSplitter(sentence);
		
		for(int i = 0; i < elements.length; i++)
		{
			if(isAlpha(elements[i].charAt(0)))
			{
				elements[i] = wordCorrector(elements[i], dictionary, game);
			}
		}
		
		return strJoin(elements);
	}
	
	public static String wordCorrector(String word, String[] dictionary, Game game)
	{
		String temp1 = word.toLowerCase();
		String temp2 = temp1;
		boolean isCapital = isCapital(word);
		
		if(isWordInDictionary(temp1, dictionary))
		{
			return word;
		}
		
		
		for(int i = 0; i < temp1.length(); i++)//letter spelling error check
		{
			for(byte  j = (byte)'a'; j <= (byte)'z'; j++)
			{
				temp2 = changeChar(temp1, i, (char)j);
				
				if(isWordInDictionary(temp2, dictionary))
				{
					game.clearScreen();
					game.println("Original Word: " + word);
					game.println("Will be change to: " + temp2);
					game.println(SPELLCORRECTOR_CHOICE1);
					game.println(SPELLCORRECTOR_CHOICE2);
					game.println(SPELLCORRECTOR_CHOICE3);
					
					while(true)
					{
						char key;
						
						try {
							key = game.readKey();
						} catch (InterruptedException e) {
							e.printStackTrace();
							return null;
						}
						
						if(key == KeyEvent.VK_1)
						{
							if(isCapital) return capitalize(temp2);
							
							return temp2;
						}
						
						if(key == KeyEvent.VK_2)
							break;
							
						if(key == KeyEvent.VK_3)
							return word;
					}
				}
			}
		}
		
		for(int i = 0; i < temp1.length()-1; i++)
		{
			
			for(int j = i + 1; j < temp1.length() - 1; j++)
			{

				temp2 = reverseChars(temp1, i, j);
				
				if(isWordInDictionary(temp2, dictionary))
				{

					game.clearScreen();
					game.println("Original Word: " + word);
					game.println("Will be change to: " + temp2);
					game.println(SPELLCORRECTOR_CHOICE1);
					game.println(SPELLCORRECTOR_CHOICE2);
					game.println(SPELLCORRECTOR_CHOICE3);
					
					while(true)
					{
						char key;
						
						try {
							key = game.readKey();
						} catch (InterruptedException e) {
							e.printStackTrace();
							return null;
						}
						
						if(key == KeyEvent.VK_1)
						{
							if(isCapital) return capitalize(temp2);
							
							return temp2;
						}
						
						if(key == KeyEvent.VK_2)
							break;
							
						if(key == KeyEvent.VK_3)
							return word;
					}
				}
			}
		}
		
		return word;
	}
	
	//Doesn't include stop words
	public static String[] wordsOfString(String str, String[] stopWords)
	{
		String[] elements = sentenceSplitter(str);
		int len = 0, lastIndex = 0;
		
		for(int i = 0; i < elements.length; i++)
		{
			if(isAlpha(elements[i].charAt(0)) && !isWordInDictionary(elements[i], stopWords))
					len++;
		}
		
		String[] words = new String[len];
		
		for(int i = 0; i < elements.length; i++)
		{
			if(isAlpha(elements[i].charAt(0)) && !isWordInDictionary(elements[i], stopWords))
			{
				words[lastIndex] = elements[i];
				lastIndex++;
			}
			
		}
		
		return words;
	}
	
	public static String[] sentenceSplitter(String sentence)
	{
		int len = 1;
		boolean lastCheck = isAlpha(sentence.charAt(0));
		
		for(int i = 0; i < sentence.length(); i++)
		{
			if(lastCheck != isAlpha(sentence.charAt(i)))
			{
				lastCheck = !lastCheck;
				len++;
			}
		}
		
		String[] elements = new String[len];
		for(int i = 0; i < elements.length; i++)
		{
			elements[i] = "";	
		}
		
		lastCheck = isAlpha(sentence.charAt(0));
		for(int i = 0, index = 0; i < sentence.length(); i++)
		{
			if(lastCheck != isAlpha(sentence.charAt(i)))
			{
				lastCheck = !lastCheck;
				index++;
			}
			
			elements[index] += sentence.charAt(i);
		}
		
		return elements;
	}
	
	public static boolean isWordInDictionary(String word, String[] dictionary)
	{
		word = word.toLowerCase();
		int min = 0, max = dictionary.length-1;
		int currentIndex;
		int comparisonRes;
		
		while(min <= max)
		{
			currentIndex = (min+max)/2;
			comparisonRes = word.compareTo(dictionary[currentIndex]);
			
			if(comparisonRes == 0)
			{
				return true;
			}
			
			if(comparisonRes > 0)
			{
				min = currentIndex+1;
				continue;
			}
			
			if(comparisonRes < 0)
			{
				max = currentIndex-1;
				continue;
			}
		}
		
		return false;
	}
	
	public static boolean isAlpha(char chr)
	{
		if((chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z'))
			return true;
		
		return false;
	}
	
	public static boolean isCapital(String word)
	{
		char initial = word.charAt(0);
		if(initial >= 'A' && initial<= 'Z')
			return true;
		
		return false;
	}
	
	public static String changeChar(String str, int index, char chr)
	{
		if(index >= str.length()) return null;
			
		return str.substring(0, index) + chr + str.substring(index+1);
	}
	
	public static String reverseChars(String str, int index1, int index2)
	{
		char[] c = str.toCharArray();

		// Replace with a "swap" function, if desired:
		char temp = c[index1];
		c[index1] = c[index2];
		c[index2] = temp;
		
		String swappedString = new String(c);
		
		return swappedString;
	}
	
	public static String capitalize(String str)
	{
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String strJoin(String[] arr)
	{
		String res = "";
		for(int i = 0; i < arr.length; i++)
		{
			res += arr[i];
		}
		
		return res;
	}
	
}
