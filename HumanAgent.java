package assignment1;

import assignment1.Exceptions.InvalidMoveException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HumanAgent extends Agent {
	
	boolean timeOver = false;

    public HumanAgent(Board board) {
        super(board);
    }

    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() {
    	
    	
    		
    	long StartTime = System.currentTimeMillis();
    	
        List<Cell> possibleCells = board.getPossibleCells();
        Cell fromCell = getFromCell(possibleCells);
        List<Cell> possibleDestinations = board.getPossibleDestinations(fromCell);
        Cell toCell = getToCell(possibleDestinations);
        
        long EndTime = System.currentTimeMillis();
        
        System.out.println("Player took " + (EndTime - StartTime)/1000 + " seconds" + " to enter move");
        if(EndTime - StartTime > 8*1000) {
        	
        	System.out.println("Random move has been chosen since player took more than 8 seconds to make move");
        	return getRandomMove();
        }
        return new Move(fromCell,toCell);
    	  
    	
    }

    private Cell getFromCell(List<Cell> possibleCells) {
        String pieceMessage = String.format("[%s] Possible pieces are %s. Enter the piece you want to move: ",
                board.getTurn().getType(), possibleCells.stream().map(Cell::getCoordinate).toList());
        Coordinate fromCoordinate = getCoordinate(pieceMessage);

        Cell fromCell = board.getCell(fromCoordinate);
        if (!possibleCells.contains(fromCell)) {
            System.out.printf("%s is invalid.%n", fromCoordinate);
            return getFromCell(possibleCells);
        }

        return fromCell;
    }

    private Cell getToCell(List<Cell> possibleDestinations) {
        String toCoordinateMessage = String.format("[%s] Possible destinations are %s. Enter where you want to move: ",
                board.getTurn().getType(), possibleDestinations.stream().map(Cell::getCoordinate).toList());
        Coordinate toCoordinate = getCoordinate(toCoordinateMessage);
        Cell toCell = board.getCell(toCoordinate);
        if (!possibleDestinations.contains(toCell)) {
            System.out.printf("%s is an invalid destination.\n", toCoordinate);
            return getToCell(possibleDestinations);
        }
        return toCell;
    }

    public static Coordinate getCoordinate(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        String coordinateStr = scanner.nextLine();
        try {
            return Utils.parseUserMove(coordinateStr.strip().toUpperCase());
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
            return getCoordinate(message);
        }
    }
    
    
    private Move getRandomMove() {
    	List<Cell> possibleCells = board.getPossibleCells();
        Cell fromCell = possibleCells.get(new Random().nextInt(possibleCells.size()));

        List<Cell> possibleDestinations = board.getPossibleDestinations(fromCell);
        Cell toCell = possibleDestinations.get(new Random().nextInt(possibleDestinations.size()));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("[%s ] Moving piece %s to %s.\n",
                board.getTurn().getType(), fromCell.getCoordinate(), toCell.getCoordinate());
        return new Move(fromCell, toCell);
    }
}