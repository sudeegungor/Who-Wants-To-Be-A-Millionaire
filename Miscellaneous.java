package utility;

import obj.Category;
import obj.Participant;
import obj.Question;

public class Miscellaneous {

	public static Category[] createCategories(Question[] questions)
	{
		Category[] categories= new Category[0];
		Category tempCategory;
		boolean flag;
		for(int i = 0; i < questions.length; i++)
		{
			flag = false;
			for(int j = 0; j < categories.length; j++)
			{
				if(categories[j].getName().equals(questions[i].getCategory()))
				{
					flag = true;
					categories[j].setQuestionAmount(categories[j].getQuestionAmount() + 1);
					break;
				}
			}
			
			if(!flag)
			{
				tempCategory = new Category(questions[i].getCategory());
				categories = ArrayRealloc.categoryArrRealloc(categories, tempCategory);
			}
		}
		
		return categories;
	}
	
    public static int randomWithRange(int min, int max) {
        if(min == max)
        {
            return min;
        }
        else {
            int range = (max - min) + 1;
            return (int) (Math.random() * range) + min;
        }
    }

	public static Participant randomParticipant(Participant[] participants)
	{
		Participant nextParticipant = null;
		
		for(int i = 0; i < participants.length; i++)
		{
			int index = randomWithRange(0, participants.length-1);
			nextParticipant = participants[index];
			
			if(!nextParticipant.getCompeted())
			{
				return nextParticipant;
			}
		}
		
		for(int i = 0; i < participants.length; i++)
		{
			nextParticipant = participants[i];
			
			if(!nextParticipant.getCompeted())
			{
				return nextParticipant;
			}
		}
		
		return null;
	}
	
	
}
