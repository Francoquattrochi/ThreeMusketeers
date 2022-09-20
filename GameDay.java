package assignment1;

public class GameDay implements GameHistory {
	String data;
	
	public GameDay(String data) {
		this.data = data;
	}

	@Override
	public void getHistory() {
		System.out.println(this.data);
		
	}

}
