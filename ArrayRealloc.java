package utility;

import obj.Category;
import obj.Participant;
import obj.Question;

public class ArrayRealloc {
		
	public static Question[] questionArrRealloc(Question[] arr, Question newQuestion)
	{
		Question[] newArr = new Question[arr.length+1];
		
		for(int i = 0; i < arr.length; i++)
		{
			newArr[i] = arr[i];
		}
		newArr[arr.length] = newQuestion;
		
		return newArr;
	}
	
	public static Participant[] participantArrRealloc(Participant[] arr, Participant newParticipant)
	{
		Participant[] newArr = new Participant[arr.length+1];
		
		for(int i = 0; i < arr.length; i++)
		{
			newArr[i] = arr[i];
		}
		newArr[arr.length] = newParticipant;
		
		return newArr;
	}
	
	public static Category[] categoryArrRealloc(Category[] arr, Category newCategory)
	{
		Category[] newArr = new Category[arr.length+1];
		
		for(int i = 0; i < arr.length; i++)
		{
			newArr[i] = arr[i];
		}
		newArr[arr.length] = newCategory;
		
		return newArr;
	}
	
}
