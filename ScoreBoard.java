package assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import assignment1.Piece.Type;

public class ScoreBoard {
	
	public ArrayList<String> lines = new ArrayList<String>();
	
	
	
	public void OpenScoreHistory() {
		File myFile = new File("Boards/Score.txt");
		
		try {
			myFile.createNewFile();
			
		} catch (IOException e) {
			
			System.out.println("Error opening score.txt file");
		}
		
	}
	
	
	
	public void saveScoreToHistory(Piece.Type winner) {
		
			String oldContent = "";
	        BufferedReader reader = null;
	        FileWriter writer = null;
	        try
	        {
	        	
//	        	Scanner myReader = new Scanner(myFile);
//				while (myReader.hasNextLine()) {
//					String data = myReader.nextLine();
//					
//					oldContent.add(data);
//				}
//	        	System.out.println(oldContent);
//	        	myReader.close()
	        
	            reader = new BufferedReader( new FileReader("Boards/Score.txt"));

	            

	            String line = reader.readLine();
	            

	            while (line != null) 
	            {
	                oldContent = oldContent + line + System.lineSeparator();

	                line = reader.readLine();
	            }
	            reader.close();
	            

	            if(winner == Type.MUSKETEER) {
	            	int Newscore = Integer.parseInt(oldScore().get(1)) + 1;
	            	String newContent = oldContent.replace(oldScore().get(1), String.valueOf(Newscore));

		            writer = new FileWriter("Boards/Score.txt");
		            writer.write(newContent);
	            	
	            }
	            else {
	            	int Newscore = Integer.parseInt(oldScore().get(3)) + 1;
	            	String newContent = oldContent.replace(oldScore().get(3), String.valueOf(Newscore));
	            	

		            writer = new FileWriter("Boards/Score.txt");
		            writer.write(newContent);
		            reader.close();
	                writer.close();
	            }
	            
	    
	            
	        }catch (IOException e) {
			System.out.println("Error adding new score to file");
	        											}
		
	}
	
	
	public ArrayList<String> oldScore() {
		
		ArrayList<String> oldContent = new ArrayList<>();
		
		
		File myFile = new File("Boards/Score.txt");
		
		try {
			Scanner myReader = new Scanner(myFile);
			
			while(myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				oldContent.add(data);
			}
			
			myReader.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Error");
		}
		
		return oldContent;
		
	}
	
		
	
	
	public static String displayScore() {
		BufferedReader reader = null;
		String content = " ";
		try {
			reader = new BufferedReader( new FileReader("Boards/Score.txt"));
			 String line = reader.readLine();
		        

		        while (line != null) 
		        {
		            content = content + line + System.lineSeparator();

		            line = reader.readLine();
		        }
		        
		        reader.close();
		        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
		

}
}
