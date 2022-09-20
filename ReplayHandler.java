package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ReplayHandler {
	Queue<Move> allMoves = new LinkedList<Move>();
	public ReplayHandler() {
		this.allMoves = new LinkedList<Move>();
	}
	
	
	public void replayReader(int index) {
		try {
			File myFile = new File("replays.txt");
			Scanner myReader = new Scanner(myFile);

			int i = 0;
			while(i < index) {
				myReader.nextLine();
				i += 1;
				
			}
			String indexData = myReader.nextLine();
			myReader.close();
			String[] splitData = indexData.split(",");
			ReplayMoveBuilder moveBuilder = new ReplayMoveBuilder();
			for(String move : splitData) {
				Move newMove = moveBuilder.buildMove(move);
				this.allMoves.add(newMove);
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("Replays File Not Found");
		}catch(NoSuchElementException e) {
			System.out.println("There Is No Replay With Such A Number,Try Again");
		}
		
		
	}
	
	public void replayGame() {
		Board newBoard = new Board();
		System.out.println("Start Of Game");
		System.out.println(newBoard);
		while(this.allMoves.size() > 0) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Move currMove = allMoves.remove();
			
			Cell fromCell = currMove.fromCell;
			Cell toCell = currMove.toCell;
			
			Cell boardTo =  newBoard.getCell(toCell.getCoordinate());
			Cell boardFrom = newBoard.getCell(fromCell.getCoordinate());
			
			boardTo.setPiece(boardFrom.getPiece());
			boardFrom.setPiece(null);

			System.out.println(currMove);
			System.out.println(newBoard);
			
		}
		
		
		
	}

}
