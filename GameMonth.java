package assignment1;

import java.util.ArrayList;

public class GameMonth implements GameHistoryObserver {
	ArrayList<GameDay> days = new ArrayList<GameDay>();
	String month;

	public GameMonth(String month) {
		this.days = new ArrayList<GameDay>();
		this.month = month;
	}
	
	public void addDay(GameDay date) {
		this.days.add(date);
	}

	@Override
	public void getHistory() {
		System.out.print(this.month + "\n");
		for(GameDay day : this.days) {
			day.getHistory();
			
		}
		
	}

}
