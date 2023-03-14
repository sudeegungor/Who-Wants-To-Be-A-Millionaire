package obj;

public class Question {
	
	private String category;
	private String text;
	private String choice1;
	private String choice2;
	private String choice3;
	private String choice4;
	private String correctAnswer;
	private int difficulty;
	private int id;
	
	public Question(String category, String text, String choice1, String choice2, String choice3, String choice4, String correctAnswer, int difficulty, int id)
	{
		this.category = category;
		this.text = text;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.correctAnswer = correctAnswer;
		this.setDifficulty(difficulty);
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public String getText() {
		return text;
	}

	public String getChoice1() {
		return choice1;
	}

	public String getChoice2() {
		return choice2;
	}

	public String getChoice3() {
		return choice3;
	}

	public String getChoice4() {
		return choice4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public int getDifficulty() {
		return difficulty;
	}

	private void setDifficulty(int difficulty) {
		if(difficulty <= 5 && difficulty >= 1)
		{
			this.difficulty = difficulty;
			return;
		}
		
		throw new IllegalArgumentException("Difficulty should in the range of [1-5].");
	}

	public int getId() {
		return id;
	}
}
