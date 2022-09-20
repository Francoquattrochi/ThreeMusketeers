package assignment1;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import assignment1.ThreeMusketeers.GameMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


public class GameHistoryHandler {
	ArrayList<GameMonth> months = new ArrayList<GameMonth>();
	public GameHistoryHandler() {
		this.months  = new ArrayList<GameMonth>();

	}
	
	public void callNested() {
		for(GameMonth month: months) {
			month.getHistory();
		}
	}
	
	public void checkForFile() {
		File myFile = new File("games.txt");
		try {
			myFile.createNewFile();
		}catch(IOException e) {
			System.out.println("There Was An Error Checking For File");
		}	
	}
	
	public void addEntry(Piece.Type winner, GameMode mode) {
		try {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String strDate = dateFormat.format(date);
		FileWriter myWriter = new FileWriter("games.txt", true);
		
		LineNumberReader reader = new LineNumberReader(new FileReader("games.txt"));
		int lineNum = 0;
		while(reader.readLine() != null){
			lineNum += 1;
		}
		reader.close();
		
		String lineNumberStr = Integer.toString(lineNum);
		
		String fileEntry =  lineNumberStr  +": "+ "||Date: " + strDate + "|| Game Mode: " + mode.toString() + "|| Winner:" + winner.toString() + "||\n";
		myWriter.write(fileEntry);
		myWriter.close();
		}catch(IOException e){
			System.out.println("There Was An Error With Saving Game Data");
			
		}
		
	}
	
	public void readData() {
		try {
			ArrayList<String> loggedMonths = new ArrayList<String>();
			File myFile = new File("games.txt");
			Scanner myReader = new Scanner(myFile);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				int startIndex = data.indexOf(":");
				String subData = data.substring(startIndex);
				String currMonth = subData.substring(15, 17);
				currMonth = this.getMonth(currMonth);

				if(!loggedMonths.contains(currMonth)){
					GameMonth newMonth = new GameMonth(currMonth);
					this.months.add(newMonth);
					loggedMonths.add(currMonth);
				}
				for(GameMonth month: this.months) {
					if(month.month == currMonth) {
						GameDay day = new GameDay(data);
						month.addDay(day);
					}
				}
				
			}
			myReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("There Was An Error Reading The File");
		}
		
		
	}
	
	public String getMonth(String monthNum) {
		switch(monthNum) {
		case "01":
			return "January";
		case "02":
			return "Febuary";
		case "03":
			return "March";
		case "04":
			return "April";
		case "05":
			return "May";
		case "06":
			return "June";
		case "07":
			return "July";
		case "08":
			return "August";
		case "09":
			return "September";
		case "10":
			return "October";
		case "11":
			return "November";
		case "12":
			return "December";
		default:
			return "Error";
				
		}
	}

}
