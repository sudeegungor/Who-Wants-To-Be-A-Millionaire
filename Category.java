package obj;

public class Category {

	private String name;
	private int questionAmount = 1;
	private int correct = 0;
	private int wrong = 0;
	
	public Category(String name)
	{
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getQuestionAmount() {
		return questionAmount;
	}

	public void setQuestionAmount(int questionAmount) {
		this.questionAmount = questionAmount;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}
	
	public void incrementCorrect()
	{
		this.correct++;
	}

	public int getWrong() {
		return wrong;
	}

	public void setWrong(int wrong) {
		this.wrong = wrong;
	}
	
	public void incrementWrong()
	{
		this.wrong++;
	}
}
