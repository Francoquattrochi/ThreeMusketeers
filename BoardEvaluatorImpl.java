package assignment1;

import java.util.List;

import assignment1.Piece.Type;

public class BoardEvaluatorImpl implements BoardEvaluator {

    /**
     * Calculates a score for the given Board
     * A higher score means the Musketeer is winning
     * A lower score means the Guard is winning
     * Add heuristics to generate a score given a Board
     * @param board Board to calculate the score of
     * @return int Score of the given Board
     */
    @Override
    public double evaluateBoard(Board board) {
    	
    	if(muskAligned(board)) {
    		return -1;
    	}
    	
    	else if( !muskAligned(board) && !muskPossibleMove(board)) {
    		return 1;
 
    	}
    	
    	return 0;
    }
    
    
    public boolean muskPossibleMove(Board board) {
    	
    	for( Cell cellsMovable: board.getPossibleCells()) {
    		if(cellsMovable.getPiece().getType() == Type.MUSKETEER) {
    			return true;
    		}
    	}
    	return false;
    }


	public boolean muskAligned(Board board) {
		
		List<Cell> musks = board.getMusketeerCells();
		
		
		int muskOneCol = musks.get(0).getCoordinate().col;
		int muskOneRow = musks.get(0).getCoordinate().row;
		
		int muskTwoCol = musks.get(1).getCoordinate().col;
		int muskTwoRow = musks.get(1).getCoordinate().row;
		
		int muskThreeCol = musks.get(2).getCoordinate().col;
		int muskThreeRow = musks.get(2).getCoordinate().row;
		
		if(muskOneCol == muskTwoCol && muskTwoCol == muskThreeCol) {
			return true;
		}
		
		if(muskOneRow == muskTwoRow && muskTwoRow == muskThreeRow) {
			return true;
		}
		
		return false;
		
	}
	
}