package obj;

public class Statistics {

	private Category worstCategory;//most wrong answers
	private Category bestCategory;//most correct answers
	private Category[] categories;
	private Participant successfulParticipant;//most successful
	private Participant[] participants;
	private double age1Average = 0;//Age<=30
	private double age2Average = 0;//30<Age<=50
	private double age3Average = 0;//50<Ag
	private int age1Competed = 0;//Age<=30
	private int age2Competed = 0;//30<Age<=50
	private int age3Competed = 0;//50<Ag
	private String highestParticipantsCity;//city with the highest amount of participants

	public void setParticipants(Participant[] participants) {
		this.participants = participants;
		updateHighestParticipantsCity();
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

	public Category getWorstCategory() {
		return worstCategory;
	}

	public Category getBestCategory() {
		return bestCategory;
	}

	public Participant getSuccessfulParticipant() {
		return successfulParticipant;
	}

	public double getAge1Average() {
		return age1Average;
	}

	public double getAge2Average() {
		return age2Average;
	}

	public double getAge3Average() {
		return age3Average;
	}

	public String getHighestParticipantsCity() {
		return highestParticipantsCity;
	}
	
	public void updateParticipantStats(Participant participant)
	{
		if(successfulParticipant == null)
		{
			successfulParticipant = participant;
		}
		else if(participant.getCorrectAnswers() > successfulParticipant.getCorrectAnswers())
		{
			successfulParticipant = participant;
		}
		
		updateAverages(participant);
	}
	
	private void updateAverages(Participant participant)
	{
		if(participant.getAge() <= 30)
		{
			double total = (age1Average * age1Competed);
			age1Competed++;
			age1Average = (double)(total + participant.getCorrectAnswers()) / age1Competed;
			return;
		}
		
		if(participant.getAge() <= 50)
		{
			double total = age2Average * age2Competed;
			age2Competed++;
			age2Average = (double)(total + participant.getCorrectAnswers()) / age2Competed;
			return;
		}
		
		double total = age3Average * age3Competed;
		age3Competed++;
		age3Average = (double)(total + participant.getCorrectAnswers()) / age3Competed;
	}
	
	public void updateCategory(Question question, boolean correct)
	{
		for(int i = 0; i < categories.length; i++)
		{
			if(categories[i].getName().equals(question.getCategory()))
			{
				if(correct)
				{
					categories[i].incrementCorrect();
					break;
				}
				
				categories[i].incrementWrong();
				break;
			}
		}
		
		updateBestCategory();
		updateWorstCategory();
	}
	
	private void updateBestCategory()
	{
		Category tempCategory = categories[0];
		
		for(int i = 0; i < categories.length; i++)
		{
			if(tempCategory.getCorrect() < categories[i].getCorrect())
			{
				tempCategory = categories[i];
			}
		}
		
		bestCategory = tempCategory;
	}
	
	private void updateWorstCategory()
	{
		Category tempCategory = categories[0];
		
		for(int i = 0; i < categories.length; i++)
		{
			if(tempCategory.getWrong() < categories[i].getWrong())
			{
				tempCategory = categories[i];
			}
		}
		
		worstCategory = tempCategory;
	}
	
	private void updateHighestParticipantsCity()
	{
		String currentHigh = null;
		int currentHighAmount = 0;
		
		for(int i = 0; i < participants.length; i++)
		{
			String tempCity = participants[i].getCity();
			int tempAmount = 1;
			
			for(int j = i+1; j < participants.length; j++)
			{
				if(participants[j].getCity().equals(tempCity))
					tempAmount++;
			}
			
			if(tempAmount > currentHighAmount)
			{
				currentHigh = tempCity;
				currentHighAmount = tempAmount;
			}
		}
		
		highestParticipantsCity = currentHigh;
	}
}
