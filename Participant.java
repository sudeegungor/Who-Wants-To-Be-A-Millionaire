package obj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Participant {
	
	private String name;
	private int birthYear;
	private int birthMonth;
	private int birthDay;
	private int age;
	private String birthDate;
	private String phone;
	private String address;
	private String city;
	private int id;
	private int earned = 0;
	private boolean competed = false;
	private int correctAnswers = 0;
	
	public Participant(String name, String birthDate, String phone, String address, int id)
	{
		this.name = name;
		this.setBirthDate(birthDate);
		this.phone = phone;
		this.setAddress(address);
		
		this.id = id;
		this.age = calcAge();
	}
	
	private int calcAge() {
		int _age;
		String[] currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).split("/");
		
		_age = Integer.parseInt(currentDate[2]) - getBirthYear();
		
		if(getBirthMonth() > Integer.parseInt(currentDate[1]))
		{
			_age--;
			return _age;
		}
		
		if(getBirthMonth() == Integer.parseInt(currentDate[1]) && getBirthDay() > Integer.parseInt(currentDate[0]))
		{
			_age--;
		}
		
		if(_age < 0)
		{
			throw new IllegalArgumentException("Invalid date");
		}
		
		return _age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	private void setAddress(String address) {
		String[] elements = address.split(";");
		
		if(elements.length != 5)
		{
			throw new IllegalArgumentException("Invalid address format");
		}
		
		this.address = address;
		this.city = elements[3];
	}

	public String getCity() {
		return city;
	}

	public int getId() {
		return id;
	}

	public String getBirthDate() {
		return birthDate;
	}

	private void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
		String[] birthArr = birthDate.split("\\.");
		
		if(birthArr.length != 3)
		{
			throw new IllegalArgumentException("Invalid date format");
		}
		
		this.birthDay = Integer.parseInt(birthArr[0]);
		this.birthMonth = Integer.parseInt(birthArr[1]);
		this.birthYear = Integer.parseInt(birthArr[2]);
	}

	public String getPhone() {
		return phone;
	}

	public int getEarned() {
		return earned;
	}

	public void setEarned(int earned) {
		this.earned = earned;
	}

	public boolean getCompeted() {
		return competed;
	}

	public void setCompeted(boolean competed) {
		this.competed = competed;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public int getBirthDay() {
		return birthDay;
	}
}
