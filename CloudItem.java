package obj;

public class CloudItem {

	private String word;
	private Question origin;
	
	public CloudItem(String word, Question origin) {
		this.word = word;
		this.origin = origin;
	}

	public String getWord() {
		return word;
	}

	public Question getOrigin() {
		return origin;
	}
}
