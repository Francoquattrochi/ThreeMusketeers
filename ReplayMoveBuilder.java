package assignment1;

public class ReplayMoveBuilder {

	public Move buildMove(String strMove) {
		int firstCoordRow = Character.getNumericValue(strMove.charAt(0));
		int firstCoordCol = Character.getNumericValue(strMove.charAt(1));
		
		int secondCoordRow = Character.getNumericValue(strMove.charAt(3));
		int secondCoordCol = Character.getNumericValue(strMove.charAt(4));
		
		Coordinate coord1 = new Coordinate(firstCoordRow, firstCoordCol);
		Coordinate coord2 = new Coordinate(secondCoordRow, secondCoordCol);
		
		Cell cell1 = new Cell(coord1);
		Cell cell2 = new Cell(coord2);
		
		Move move = new Move(cell1, cell2);
		return move;
		
	}

}
